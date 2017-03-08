package com.wolf.shoot.common.thread.policy;

/**
 * Created by jwp on 2017/3/8.
 */
public enum RejectedPolicyType {
    ABORT_POLICY("AbortPolicy"),
    BLOCKING_POLICY("BlockingPolicy"),
    CALLER_RUNS_POLICY("CallerRunsPolicy"),
    DISCARDED_POLICY("DiscardedPolicy"),
    REJECTED_POLICY("RejectedPolicy");

    private String value;

    private RejectedPolicyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RejectedPolicyType fromString(String value) {
        for (RejectedPolicyType type : RejectedPolicyType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mismatched type with value=" + value);
    }

    public String toString() {
        return value;
    }
}