import {Timestamp} from "rxjs";
import {Product} from "./product";

export interface Order {
    accountId: number;
    productsIds: number[];
    timestamp: string;


}