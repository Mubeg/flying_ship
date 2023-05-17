package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public enum MessagesTypes {
    Bad, // = 0
    RequestSettings,
    SettingsUpdate,
    Pause,
    Stop,
    Resume,
    UpdateFrame,
    Checkin,
    ReCheckin,
    RequestInfo,
    SendInfo,
    End,
    StartGame,
    PlayerLogIn,
    UpdateCursor,
    GameOver;
   
    public byte value(){
        return (byte) ordinal();
    }
}
