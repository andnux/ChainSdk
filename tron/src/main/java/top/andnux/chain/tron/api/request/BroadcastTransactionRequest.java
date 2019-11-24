package top.andnux.chain.tron.api.request;

import java.io.Serializable;
import java.util.List;

public class BroadcastTransactionRequest implements Serializable {

    /**
     * signature : ["97c825b41c77de2a8bd65b3df55cd4c0df59c307c0187e42321dcc1cc455ddba583dd9502e17cfec5945b34cad0511985a6165999092a6dec84c2bdd97e649fc01"]
     * txID : 454f156bf1256587ff6ccdbc56e64ad0c51e4f8efea5490dcbc720ee606bc7b8
     * raw_data : {"contract":[{"parameter":{"value":{"amount":1000,"owner_address":"41e552f6487585c2b58bc2c9bb4492bc1f17132cd0","to_address":"41d1e7a6bc354106cb410e65ff8b181c600ff14292"},"type_url":"type.googleapis.com/protocol.TransferContract"},"type":"TransferContract"}],"ref_block_bytes":"267e","ref_block_hash":"9a447d222e8de9f2","expiration":1530893064000,"timestamp":1530893006233}
     */

    private String txID;
    private RawDataBean raw_data;
    private List<String> signature;

    public String getTxID() {
        return txID;
    }

    public void setTxID(String txID) {
        this.txID = txID;
    }

    public RawDataBean getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(RawDataBean raw_data) {
        this.raw_data = raw_data;
    }

    public List<String> getSignature() {
        return signature;
    }

    public void setSignature(List<String> signature) {
        this.signature = signature;
    }

    public static class RawDataBean implements Serializable {
        /**
         * contract : [{"parameter":{"value":{"amount":1000,"owner_address":"41e552f6487585c2b58bc2c9bb4492bc1f17132cd0","to_address":"41d1e7a6bc354106cb410e65ff8b181c600ff14292"},"type_url":"type.googleapis.com/protocol.TransferContract"},"type":"TransferContract"}]
         * ref_block_bytes : 267e
         * ref_block_hash : 9a447d222e8de9f2
         * expiration : 1530893064000
         * timestamp : 1530893006233
         */

        private String ref_block_bytes;
        private String ref_block_hash;
        private long expiration;
        private long timestamp;
        private List<ContractBean> contract;

        public String getRef_block_bytes() {
            return ref_block_bytes;
        }

        public void setRef_block_bytes(String ref_block_bytes) {
            this.ref_block_bytes = ref_block_bytes;
        }

        public String getRef_block_hash() {
            return ref_block_hash;
        }

        public void setRef_block_hash(String ref_block_hash) {
            this.ref_block_hash = ref_block_hash;
        }

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public List<ContractBean> getContract() {
            return contract;
        }

        public void setContract(List<ContractBean> contract) {
            this.contract = contract;
        }

        public static class ContractBean implements Serializable {
            /**
             * parameter : {"value":{"amount":1000,"owner_address":"41e552f6487585c2b58bc2c9bb4492bc1f17132cd0","to_address":"41d1e7a6bc354106cb410e65ff8b181c600ff14292"},"type_url":"type.googleapis.com/protocol.TransferContract"}
             * type : TransferContract
             */

            private ParameterBean parameter;
            private String type;

            public ParameterBean getParameter() {
                return parameter;
            }

            public void setParameter(ParameterBean parameter) {
                this.parameter = parameter;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class ParameterBean implements Serializable {
                /**
                 * value : {"amount":1000,"owner_address":"41e552f6487585c2b58bc2c9bb4492bc1f17132cd0","to_address":"41d1e7a6bc354106cb410e65ff8b181c600ff14292"}
                 * type_url : type.googleapis.com/protocol.TransferContract
                 */

                private ValueBean value;
                private String type_url;

                public ValueBean getValue() {
                    return value;
                }

                public void setValue(ValueBean value) {
                    this.value = value;
                }

                public String getType_url() {
                    return type_url;
                }

                public void setType_url(String type_url) {
                    this.type_url = type_url;
                }

                public static class ValueBean implements Serializable {
                    /**
                     * amount : 1000
                     * owner_address : 41e552f6487585c2b58bc2c9bb4492bc1f17132cd0
                     * to_address : 41d1e7a6bc354106cb410e65ff8b181c600ff14292
                     */

                    private int amount;
                    private String owner_address;
                    private String to_address;

                    public int getAmount() {
                        return amount;
                    }

                    public void setAmount(int amount) {
                        this.amount = amount;
                    }

                    public String getOwner_address() {
                        return owner_address;
                    }

                    public void setOwner_address(String owner_address) {
                        this.owner_address = owner_address;
                    }

                    public String getTo_address() {
                        return to_address;
                    }

                    public void setTo_address(String to_address) {
                        this.to_address = to_address;
                    }
                }
            }
        }
    }
}
