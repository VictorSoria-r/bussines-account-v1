package com.codepar.bussinesaccountv1.domains.account.domain.type;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    CURRENT("01", "Corriente"),
    SAVINGS("11", "Ahorro");

    private final String code;
    private final String shortName;

    private static ImmutableMap<String, ProductType> reverseLookupCode = Maps.uniqueIndex(Arrays.asList(ProductType.values()), ProductType::getCode);

    public static ProductType fromCode(String code) {
        ProductType productType = reverseLookupCode.get(code);
        if (productType != null) {
            return productType;
        }
        throw new IllegalArgumentException("Invalid product type");
    }
}
