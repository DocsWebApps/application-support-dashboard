import { Moment } from 'moment';

export interface IIncidentUpdates {
    id?: number;
    updatedAt?: Moment;
    updateText?: any;
    inUpdateId?: number;
}

export class IncidentUpdates implements IIncidentUpdates {
    constructor(public id?: number, public updatedAt?: Moment, public updateText?: any, public inUpdateId?: number) {}
}
