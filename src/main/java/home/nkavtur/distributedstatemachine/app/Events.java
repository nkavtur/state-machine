package home.nkavtur.distributedstatemachine.app;

import java.util.Optional;

public enum Events {

    IN_PROGRESS,
    PAUSED,
    COMPLETING,
    REMOVING,
    COMPLETED;

    public byte[] toBytesArray() {
        return this.name().getBytes();
    }

    public static Events fromBytesArray(byte[] data) {
        return Optional.ofNullable(data)
                .map(String::new)
                .map(Events::valueOf)
                .orElse(null);
    }
}
