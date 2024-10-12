import {Account} from "./account";

export interface Profile{
    id: number;
    name: string;
    phone: string;
    account: Account;
}
