package com.globant.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private String name;
    private String tradeMark;
    private int stock;
    private double price;
    private String description;
    private String tags;
    private boolean active;
    private String id;
}
