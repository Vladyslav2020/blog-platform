package com.example.blogplatform.security;

public enum Permission {
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    USER_MANAGEMENT("user:management"),
    POST_READ("post:read"),
    POST_CREATE("post:create"),
    POST_UPDATE("post:update"),
    POST_DELETE("post:delete"),
    POST_MANAGEMENT("post:management"),
    TAG_CREATE("tag:create"),
    TAG_UPDATE("tag:update"),
    TAG_DELETE("tag:delete"),
    TAG_MANAGEMENT("tag:management"),
    COMMENT_READ("comment:read"),
    COMMENT_CREATE("comment:create"),
    COMMENT_UPDATE("comment:update"),
    COMMENT_DELETE("comment:delete"),
    COMMENT_MANAGEMENT("comment:management");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
