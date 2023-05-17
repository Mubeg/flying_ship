
#include "messenger.h"

std::vector<message_t> messages;
std::mutex messages_mutex;

JNIEXPORT jbyte JNICALL Java_JNI_flyingship_src_Messenger_getMessageNative
    (JNIEnv *env, jobject obj, jbyte receiver, jbyteArray jbuff)
{
    jbyte *buff = (env)->GetByteArrayElements(jbuff, NULL);
    if (buff == NULL) {
        return JNI_ERR; 
    } 

    jchar err = JNI_ERR;

    messages_mutex.lock();

    for(auto msg = messages.begin(); msg != messages.end(); msg++){
        if(msg->receiver == receiver){
            std::memcpy(buff, &(*msg), sizeof(message_t));
            #ifdef DEBUG
            printf("Getting message with type %d, reciever %d, sender %d\nData[%d]:\n%s\n", ((message_t *)buff)->type, ((message_t *)buff)->receiver, ((message_t *)buff)->sender, DATA_LEN, ((message_t *)buff)->data);
            #endif
            messages.erase(msg);
            err = 0;
            break;
        }
    }

    messages_mutex.unlock();

    (env)->ReleaseByteArrayElements(jbuff, buff, 0);

    return err;
}


JNIEXPORT void JNICALL Java_JNI_flyingship_src_Messenger_sendMessageNative
    (JNIEnv *env, jobject obj, jbyteArray msg, int msg_len)
{
    jbyte *buff = (env)->GetByteArrayElements(msg, NULL);
    if (buff == NULL) {
        return; 
    } 

    message_t *message = nullptr;
    #ifdef DEBUG
    printf("Sending message with length %d, needed is %ld\n", msg_len, sizeof(message_t));
    #endif
    if(msg_len == sizeof(message_t))
    {
        message = reinterpret_cast<message_t *>(buff);
        messages_mutex.lock();
        messages.push_back(*message);
        messages_mutex.unlock();
        #ifdef DEBUG
        printf("Sending message with type %d, reciever %d, sender %d\nData[%d]:\n%s\n", message->type, message->receiver, message->sender, DATA_LEN, message->data);
        #endif
    }

    (env)->ReleaseByteArrayElements(msg, buff, 0);

}

Messenger::Messenger(char id) : my_id(id)
{
    messages_q = &messages;
}


/*
Receives one message designated to this reciever
@return message_t struct. If no message is found, returns Bad type
*/
message_t Messenger::get_message()
{
    message_t message = {};
    messages_mutex.lock();
    for(auto msg = messages_q->begin(); msg != messages_q->end(); msg++){
        if(msg->receiver == my_id){
            message = *msg;
            #ifdef DEBUG
            printf("Getting message with type %d, reciever %d, sender %d\nData[%d]:\n%s\n", msg->type, msg->receiver, msg->sender, DATA_LEN, msg->data);
            #endif
            messages.erase(msg);
            break;
        }
    }
    messages_mutex.unlock();

    return message;
}


/*
Sends a message decribes in msg structure
@param msg message to be sent (sender will be set to messenger's id)
*/
void Messenger::send_message(message_t msg)
{
    msg.sender = my_id;
    messages_mutex.lock();
    messages.push_back(msg);
    #ifdef DEBUG
    printf("Sending message with type %d, reciever %d, sender %d\nData[%d]:\n%s\n", msg.type, msg.receiver, msg.sender, DATA_LEN, msg.data);
    #endif
    messages_mutex.unlock();
}