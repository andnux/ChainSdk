package v.systems;

import com.google.gson.JsonElement;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.java.curve_sigs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import top.andnux.chain.core.Base58;
import v.systems.entity.BalanceDetail;
import v.systems.entity.Result;
import v.systems.entity.SlotInfo;
import v.systems.error.ApiError;
import v.systems.error.KeyError;
import v.systems.error.SerializationError;
import v.systems.error.VException;
import v.systems.serialization.BytesSerializable;
import v.systems.transaction.ContendSlotTransaction;
import v.systems.transaction.ProvenTransaction;
import v.systems.transaction.ReleaseSlotTransaction;
import v.systems.transaction.Transaction;
import v.systems.transaction.TransactionFactory;
import v.systems.type.NetworkType;
import v.systems.type.TransactionType;
import v.systems.utils.BytesHelper;
import v.systems.utils.Hash;

public class Account {

    private static final Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);
    private static final byte ADDR_VERSION = 5;

    private byte[] privateKey;
    private byte[] publicKey;
    private byte[] address;
    private NetworkType network;

    public Account(NetworkType network, String seed, Integer nonce) {
        this.network = network;
        if (nonce != null) {
            seed = nonce.toString() + seed;
        }
        byte[] seedBytes = seed.getBytes(StandardCharsets.UTF_8);
        byte[] accountSeed = Hash.secureHash(seedBytes);
        byte[] hashedSeed = Hash.sha256(accountSeed, 0, accountSeed.length);
        privateKey = Arrays.copyOf(hashedSeed, 32);
        privateKey[0] &= 248;
        privateKey[31] &= 127;
        privateKey[31] |= 64;
        publicKey = new byte[32];
        curve_sigs.curve25519_keygen(publicKey, privateKey);
        address = getAddress(publicKey, network.toByte());
    }

    public Account(NetworkType network, String base58PrivateKey) {
        this.network = network;
        privateKey = Base58.decode(base58PrivateKey);
        publicKey = new byte[32];
        curve_sigs.curve25519_keygen(publicKey, privateKey);
        address = getAddress(publicKey, network.toByte());
    }

    public Account(NetworkType network, String base58PublicKey, String base58Address) {
        this.network = network;
        if (base58PublicKey != null) {
            publicKey = Base58.decode(base58PublicKey);
            address = getAddress(publicKey, network.toByte());
        } else {
            address = Base58.decode(base58Address);
            if (!checkAddress()) {
                throw new RuntimeException("invalid address");
            }
        }
    }

    public ProvenTransaction sendTransaction(Blockchain chain, ProvenTransaction tx)
            throws SerializationError, KeyError, IOException, ApiError {
        TransactionType txType = TransactionType.parse(tx.getType());
        String signature = getSignature(tx);
        JsonElement json = tx.toAPIRequestJson(this.getPublicKey(), signature);
        return chain.sendTransaction(txType, json.toString());
    }

    public String getSignature(BytesSerializable tx) throws SerializationError, KeyError {
        return getSignature(tx.toBytes());
    }

    public String getSignature(byte[] context) throws KeyError {
        if (privateKey == null) {
            throw new KeyError("Cannot sign the context. No private key in account.");
        }
        return Base58.encode(cipher.calculateSignature(this.privateKey, context));
    }

    public boolean verifySignature(byte[] context, String signature) {
        byte[] signatureBytes = Base58.decode(signature);
        return cipher.verifySignature(this.publicKey, context, signatureBytes);
    }

    public Long getBalance(Blockchain chain) throws KeyError, IOException, ApiError {
        return chain.getBalance(this.getAddress());
    }

    public BalanceDetail getBalanceDetail(Blockchain chain) throws KeyError, IOException, ApiError {
        return chain.getBalanceDetail(this.getAddress());
    }

    public List<Transaction> getTransactionHistory(Blockchain chain, int num) throws KeyError, IOException, ApiError {
        return chain.getTransactionHistory(this.getAddress(), num);
    }

    public Result checkContend(Blockchain chain, int slotId) {
        int slotGap = this.network == NetworkType.Mainnet ? 4 : 1;
        return checkContend(chain, slotId, slotGap);
    }

    public Result checkContend(Blockchain chain, int slotId, int slotGap) {
        try {
            if (0 > slotId || slotId >= 60 || slotId % slotGap != 0) {
                return Result.fail("Invalid slot ID");
            }
            BalanceDetail balance = chain.getBalanceDetail(this.getAddress());
            if (balance.getAvailable() < TransactionFactory.CONTEND_TX_FEE) {
                return Result.fail("Insufficient balance for sending contend!");
            }
            Long minEffectiveBalance = ContendSlotTransaction.MIN_EFFECTIVE_BALANCE + TransactionFactory.CONTEND_TX_FEE;
            if (balance.getEffective() < minEffectiveBalance) {
                return Result.fail(String.format("The effective balance must greater than %d!", minEffectiveBalance));
            }
            SlotInfo slotInfo = chain.getSlotInfo(slotId);
            if (balance.getMintingAverage() <= slotInfo.getMintingAverageBalance()) {
                return Result.fail("The minting average balance of target slot is greater than or equals to yours.");
            }
            return Result.success();
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Transaction contendSlot(Blockchain chain, int slotId) throws VException, IOException {
        Result result = checkContend(chain, slotId);
        if (!result.isOk()) {
            throw new VException(result.getMessage());
        }
        ContendSlotTransaction tx = TransactionFactory.buildContendSlotTx(slotId);
        return this.sendTransaction(chain, tx);
    }

    public Transaction releaseSlot(Blockchain chain, int slotId) throws VException, IOException {
        ReleaseSlotTransaction tx = TransactionFactory.buildReleaseSlotTx(slotId);
        return this.sendTransaction(chain, tx);
    }

    public String getPrivateKey() throws KeyError {
        if (privateKey == null) {
            throw new KeyError("No private key in account.");
        }
        return Base58.encode(privateKey);
    }

    public String getPublicKey() throws KeyError {
        if (publicKey == null) {
            throw new KeyError("No public key in account.");
        }
        return Base58.encode(publicKey);
    }

    public String getAddress() throws KeyError {
        if (address == null) {
            if (publicKey == null) {
                throw new KeyError("No public key in account.");
            }
            address = getAddress(publicKey, network.toByte());
        }
        return Base58.encode(address);
    }

    public static String getAddress(String publicKey, byte networkByte) {
        return Base58.encode(getAddress(Base58.decode(publicKey), networkByte));
    }

    public static byte[] getAddress(byte[] publicKey, byte networkByte) {
        byte[] hash = Hash.secureHash(publicKey);
        byte[] addressWithoutChecksum = new byte[22];
        addressWithoutChecksum[0] = ADDR_VERSION;
        addressWithoutChecksum[1] = networkByte;
        System.arraycopy(hash, 0, addressWithoutChecksum, 2 ,20);
        byte[] checksumHash = Hash.secureHash(addressWithoutChecksum);
        byte[] checksum = new byte[4];
        System.arraycopy(checksumHash, 0, checksum, 0 ,4);
        return BytesHelper.concat(addressWithoutChecksum, checksum);
    }

    public boolean checkAddress() {
        return checkAddress(this.network, this.address);
    }

    public static boolean checkAddress(NetworkType network, String base58Address) {
        try {
            return checkAddress(network, Base58.decode(base58Address));
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean checkAddress(NetworkType network, byte[] address) {
        if (address.length != 26 || address[0] != ADDR_VERSION || address[1] != network.toByte()) {
            return false;
        }
        byte[] expectedChecksum = new byte[4];
        System.arraycopy(address, 22, expectedChecksum, 0 ,4);
        byte[] actualChecksum = Hash.secureHash(address, 0, 22);
        for (int i = 0; i < 4; i++) {
            if (expectedChecksum[i] != actualChecksum[i]) {
                return false;
            }
        }
        return true;
    }
}
