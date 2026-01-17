package com.mani.lerning.repository;

import com.mani.lerning.entity.TenantFutureConfig;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantFutureConfigRepository
        extends JpaRepository<TenantFutureConfig, Long> {

    Optional<TenantFutureConfig> findByTenantIdAndFutureKeyAndIsActiveTrue(
            String tenantId, String futureKey);

    boolean existsByTenantIdAndFutureKeyAndIsActiveTrue(
            String tenantId, String futureKey);

    List<TenantFutureConfig> findAllByTenantIdAndIsActiveTrue(String tenantId);

    /* ================= SOFT DELETE ================= */

    @Modifying
    @Transactional
    @Query("""
                update TenantFutureConfig t
                set t.isActive = false
                where t.tenantId = :tenantId
                and t.futureKey = :futureKey
            """)
    void softDelete(@Param("tenantId") String tenantId,
                    @Param("futureKey") String futureKey);

    /* ================= JSONB QUERIES ================= */

    @Query(value = """
                select *
                from tenant_future_config
                where tenant_id = :tenantId
                  and is_active = true
                  and jsonb_exists(config, :jsonKey)
            """, nativeQuery = true)
    List<TenantFutureConfig> findByJsonKey(
            @Param("tenantId") String tenantId,
            @Param("jsonKey") String jsonKey);


    @Query(value = """
                select * from tenant_future_config
                where tenant_id = :tenantId
                and is_active = true
                and config @> cast(:json as jsonb)
            """, nativeQuery = true)
    List<TenantFutureConfig> findByJsonContains(
            @Param("tenantId") String tenantId,
            @Param("json") String json);
}
