import {WeChatUser} from "./we-chat-user";

export interface User {
  id: number;
  name: string;
  username: string;
  password: string;
  weChatUser: WeChatUser;
}
