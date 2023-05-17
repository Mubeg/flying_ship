
#include "messenger.h"

std::vector<message_t> messages;
std::mutex messages_mutex;
bool Logger_is_active = false;
bool Backend_is_active = false;
bool Frontend_is_active = false;
bool Database_is_active = false;
bool Overseer_is_active = false;

JNIEXPORT jint JNICALL Java_JNI_flyingship_src_Messenger_getMessageNative
	(JNIEnv *env, jobject obj, jint receiver, jintArray jbuff)
{
	jint *buff = (env)->GetIntArrayElements(jbuff, NULL);
	if (buff == NULL) {
		return JNI_ERR; 
	} 

	jint err = JNI_ERR;

	messages_mutex.lock();
	bool success = false;
	for(auto msg = messages.begin(); msg != messages.end(); msg++){
		if(msg->receiver == receiver){
			std::memcpy(buff, &(*msg), sizeof(message_t));
			messages.erase(msg);
			err = 0;
			break;
			success = true;
		}
	}

	messages_mutex.unlock();

	#ifdef DEBUG
	if(success){
		printf("Getting message with type %d, reciever %d, sender %d\nData[%d]: %d\n", ((message_t *)buff)->type, ((message_t *)buff)->receiver, ((message_t *)buff)->sender, DATA_LEN, ((message_t *)buff)->data[0]);
	}
	else{
		//printf("#%d tried to get message, but failed\n", receiver);
	}
	#endif

	(env)->ReleaseIntArrayElements(jbuff, buff, 0);

	return err;
}

#define SEND_MSG \
		messages_mutex.lock(); \
		messages.push_back(*msg); \
		messages_mutex.unlock();

void _send_message(message_t *msg)
{

	#ifdef DEBUG
		printf("Sending message with type %d, reciever %d, sender %d\nData[%d]: %d\n", msg->type, msg->receiver, msg->sender, DATA_LEN, msg->data[0]);
	#endif
	switch (msg->receiver)
	{
		case msg::MessengingSystem:
			switch (msg->type)
			{
			case msg::RequestInfo:
				msg->type = msg::SendInfo;
				msg->receiver = msg->sender;
				msg->sender = msg::MessengingSystem;
				msg->data[0] = Logger_is_active ? 1 : 0;
				msg->data[1] = Backend_is_active ? 1 : 0;
				msg->data[2] = Frontend_is_active ? 1 : 0;
				msg->data[3] = Database_is_active ? 1 : 0;
				msg->data[4] = Overseer_is_active ? 1 : 0;
				msg->data[5] = 1; //MessagingSystem_is_active;
				SEND_MSG;
			
			default:
				break;
			}
		break;
		default:
			bool checking_in = false;
			switch (msg->type)
			{
				case msg::Checkin:
				checking_in = true;
				case msg::ReCheckin:
					switch(msg->sender)
					{
						case msg::Backend:
							Backend_is_active = checking_in;
							break;
						case msg::Database:
							Database_is_active = checking_in;
							break;
						case msg::Logger:
							Logger_is_active = checking_in;
							break;
						case msg::Frontend:
							Frontend_is_active = checking_in;
							break;
						case msg::Overseer:
							Overseer_is_active = checking_in;
							break;
						default:
							break;
					}
				default:
					SEND_MSG;
					#ifdef DEBUG
					printf("Sending message with type %d, reciever %d, sender %d\nData[%d]: %d\n", msg->type, msg->receiver, msg->sender, DATA_LEN, msg->data[0]);
					#endif
					if(msg->type == msg::UpdateFrame || msg->type == msg::GameOver || msg->type =- msg::StartGame)
					{
						msg->receiver = msg::Database;
						SEND_MSG;
					}
					if(Logger_is_active && msg->receiver != msg::Logger)
					{
						msg->receiver = msg::Logger;
						SEND_MSG;
					}
				break;
			}
		break;
	}
}


JNIEXPORT void JNICALL Java_JNI_flyingship_src_Messenger_sendMessageNative
	(JNIEnv *env, jobject obj, jintArray msg, int msg_len)
{
	jint *buff = (env)->GetIntArrayElements(msg, NULL);
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
		_send_message(message);
	}

	(env)->ReleaseIntArrayElements(msg, buff, 0);

}

Messenger::Messenger(Elem_t id) : my_id(id)
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
			printf("C++ Getting message with type %d, reciever %d, sender %d\nData[%d]: %d\n", msg->type, msg->receiver, msg->sender, DATA_LEN, msg->data[0]);
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
	_send_message(&msg);
}