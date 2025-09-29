package com.example.clothing_stores.Dto.requests;

import java.util.List;

public record RoleRequest (String name, String description, List<String> permissions){}
