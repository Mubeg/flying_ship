package JNI.flyingship.src;

/**
 *
 * @author Mubeg
 */
public enum MessagesTypes {
    BadType,
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
    GameOver,
    UpdateCursor;
   
    public int value(){
        return (int) ordinal();
    }
}
