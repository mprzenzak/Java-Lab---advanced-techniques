package com.mprzenzak.lab06.enums;

public enum ServiceType {
    ROUTER_INSTALLATION, ROUTER_REPAIR, ROUTER_UPGRADE, ROUTER_REPLACEMENT, ROUTER_REMOVAL;

    public String getLabel() {
        return switch (this) {
            case ROUTER_INSTALLATION -> "Instalacja routera";
            case ROUTER_REPAIR -> "Naprawa routera";
            case ROUTER_UPGRADE -> "Aktualizacja routera";
            case ROUTER_REPLACEMENT -> "Wymiana routera";
            case ROUTER_REMOVAL -> "Usunięcie routera";
        };
    }
}
