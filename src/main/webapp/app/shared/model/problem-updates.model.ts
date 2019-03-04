import { Moment } from 'moment';

export interface IProblemUpdates {
    id?: number;
    updatedAt?: Moment;
    updateText?: any;
    probUpdateId?: number;
}

export class ProblemUpdates implements IProblemUpdates {
    constructor(public id?: number, public updatedAt?: Moment, public updateText?: any, public probUpdateId?: number) {}
}
