import { Moment } from 'moment';

export interface IRiskUpdates {
    id?: number;
    updatedAt?: Moment;
    updateText?: any;
    riskkUpdateId?: number;
}

export class RiskUpdates implements IRiskUpdates {
    constructor(public id?: number, public updatedAt?: Moment, public updateText?: any, public riskkUpdateId?: number) {}
}
