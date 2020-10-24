package me.mk3ext.dev.core.punish;

import javafx.util.Pair;
import me.github.wert.Core.punish.reason.*;
import me.mk3ext.dev.core.punish.reason.*;

import java.sql.SQLException;
import java.util.UUID;

public class GetDuration {

    public static Pair<Integer, Punish.Punishments> getDurationS(String uuid, Reasons reason) throws SQLException {
        return new Pair<>((getDurationH(uuid, reason).getKey() * 3600), getDurationH(uuid, reason).getValue());
    }

    public static Pair<Integer, Punish.Punishments> getDurationH(String uuid, Reasons reason) throws SQLException {
        UUID id = UUID.fromString(uuid);
        String type = "";
        for (ChatReasons1 r : ChatReasons1.values()) {
            if (r.name().equals(reason.toString())) {
                type = "ChatReasons1";
                break;
            }
        }
        for (ChatReasons2 r : ChatReasons2.values()) {
            if (r.name().equals(reason.toString())) {
                type = "ChatReasons2";
                break;
            }
        }
        for (GameplayReasons r : GameplayReasons.values()) {
            if (r.name().equals(reason.toString())) {
                type = "GameplayReasons";
                break;
            }
        }
        for (ClientReasons r : ClientReasons.values()) {
            if (r.name().equals(reason.toString())) {
                type = "ClientReasons";
                break;
            }
        }
        if (type.equals("")) {
            return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.WARN);
        }

        if (type.equalsIgnoreCase("ChatReasons1")) {
            if (DatabaseHandler.getChatGoodPunishments(uuid).isEmpty()) {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.WARN);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 1) {
                return new Pair<Integer, Punish.Punishments>(2, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 2) {
                return new Pair<Integer, Punish.Punishments>(4, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 3) {
                return new Pair<Integer, Punish.Punishments>(8, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 4) {
                return new Pair<Integer, Punish.Punishments>(16, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 5) {
                return new Pair<Integer, Punish.Punishments>(32, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 6) {
                return new Pair<Integer, Punish.Punishments>(168, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatGoodPunishments(uuid).size() == 7) {
                return new Pair<Integer, Punish.Punishments>(720, Punish.Punishments.TEMPMUTE);
            } else {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.MUTE);
            }
        }

        if (type.equalsIgnoreCase("ChatReasons2")) {
            if (DatabaseHandler.getChatBadPunishments(uuid).isEmpty()) {
                return new Pair<Integer, Punish.Punishments>(2, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatBadPunishments(uuid).size() == 1) {
                return new Pair<Integer, Punish.Punishments>(4, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatBadPunishments(uuid).size() == 2) {
                return new Pair<Integer, Punish.Punishments>(8, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatBadPunishments(uuid).size() == 3) {
                return new Pair<Integer, Punish.Punishments>(16, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatBadPunishments(uuid).size() == 4) {
                return new Pair<Integer, Punish.Punishments>(32, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatBadPunishments(uuid).size() == 5) {
                return new Pair<Integer, Punish.Punishments>(168, Punish.Punishments.TEMPMUTE);
            } else if (DatabaseHandler.getChatBadPunishments(uuid).size() == 6) {
                return new Pair<Integer, Punish.Punishments>(720, Punish.Punishments.TEMPMUTE);
            } else {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.MUTE);
            }
        }

        if (type.equalsIgnoreCase("GameplayReasons")) {
            if (DatabaseHandler.getGameplayPunishments(uuid).isEmpty()) {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.WARN);
            } else if (DatabaseHandler.getGameplayPunishments(uuid).size() == 1) {
                return new Pair<Integer, Punish.Punishments>(72, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getGameplayPunishments(uuid).size() == 2) {
                return new Pair<Integer, Punish.Punishments>(168, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getGameplayPunishments(uuid).size() == 3) {
                return new Pair<Integer, Punish.Punishments>(336, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getGameplayPunishments(uuid).size() == 4) {
                return new Pair<Integer, Punish.Punishments>(720, Punish.Punishments.TEMPBAN);
            } else {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.MUTE);
            }
        }

        if (reason == Reasons.KillAura || reason == Reasons.Jesus || reason == Reasons.AntiKnockback || reason == Reasons.NoSlowdown) {
            if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).isEmpty()) {
                return new Pair<Integer, Punish.Punishments>(720, Punish.Punishments.TEMPBAN);
            } else {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.BAN);
            }
        } else if (reason == Reasons.Fly || reason == Reasons.Speed) {
            return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.BAN);
        } else if (reason == Reasons.UnapprovedMods) {
            if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).isEmpty()) {
                return new Pair<Integer, Punish.Punishments>(24, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).size() == 1){
                return new Pair<Integer, Punish.Punishments>(72, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).size() == 2) {
                return new Pair<Integer, Punish.Punishments>(168, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).size() == 1) {
                return new Pair<Integer, Punish.Punishments>(720, Punish.Punishments.TEMPBAN);
            } else {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.BAN);
            }
        } else if (reason == Reasons.Xray) {
            if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).isEmpty()) {
                return new Pair<Integer, Punish.Punishments>(168, Punish.Punishments.TEMPBAN);
            } else if (DatabaseHandler.getSpecificPunishments(uuid, reason.toString()).size() == 1) {
                return new Pair<Integer, Punish.Punishments>(720, Punish.Punishments.TEMPBAN);
            } else {
                return new Pair<Integer, Punish.Punishments>(-1, Punish.Punishments.BAN);
            }
        }
        return new Pair<>(2, Punish.Punishments.WARN);
    }
}
