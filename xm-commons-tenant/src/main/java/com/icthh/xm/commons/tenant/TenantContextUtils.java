package com.icthh.xm.commons.tenant;

import java.util.Optional;

/**
 * The {@link TenantContextUtils} class.
 */
public final class TenantContextUtils {

    /**
     * Get tenant key value by holder.
     *
     * @param holder tenant context's holder
     * @return tenant key for current thread
     */
    public static String getRequiredTenantKeyValue(TenantContextHolder holder) {
        return getRequiredTenantKeyValue(holder.getContext());
    }

    /**
     * Get tenant key value by context.
     *
     * @param context tenant context
     * @return tenant key for current thread
     */
    public static String getRequiredTenantKeyValue(TenantContext context) {
        return getRequiredTenantKey(context).getValue();
    }

    /**
     * Get tenant key object by holder.
     *
     * @param holder tenant context's holder
     * @return tenant key object for current thread
     */
    public static TenantKey getRequiredTenantKey(TenantContextHolder holder) {
        return getRequiredTenantKey(holder.getContext());
    }

    /**
     * Get tenant key object by context.
     *
     * @param context tenant context
     * @return tenant key object for current thread
     */
    public static TenantKey getRequiredTenantKey(TenantContext context) {
        return context.getTenantKey()
            .orElseThrow(() -> new IllegalStateException("Tenant context doesn't have tenant key"));
    }

    /**
     * Get tenant key optional object by holder.
     *
     * @param holder tenant context's holder
     * @return optional tenant key for current thread
     */
    public static Optional<TenantKey> getTenantKey(TenantContextHolder holder) {
        return holder.getContext().getTenantKey();
    }

    /**
     * Sets current thread tenant.
     *
     * @param holder         tenant contexts holder
     * @param tenantKeyValue tenant key value
     */
    public static void setTenant(TenantContextHolder holder, String tenantKeyValue) {
        holder.getPrivilegedContext().setTenant(buildTenant(tenantKeyValue));
    }

    /**
     * Sets current thread tenant.
     *
     * @param holder    tenant contexts holder
     * @param tenantKey tenant key
     */
    public static void setTenant(TenantContextHolder holder, TenantKey tenantKey) {
        holder.getPrivilegedContext().setTenant(buildTenant(tenantKey));
    }

    /**
     * Build {@link Tenant} instance by key value.
     *
     * @param tenantKeyValue tenant key value
     * @return instance of {@link Tenant}
     */
    public static Tenant buildTenant(String tenantKeyValue) {
        return buildTenant(TenantKey.valueOf(tenantKeyValue));
    }

    /**
     * Build {@link Tenant} instance by key.
     *
     * @param tenantKey tenant key
     * @return instance of {@link Tenant}
     */
    public static Tenant buildTenant(TenantKey tenantKey) {
        return new PlainTenant(tenantKey);
    }

    /**
     * Private utils class constructor.
     */
    private TenantContextUtils() {
        throw new IllegalAccessError("access not allowed");
    }

}