import { Moment } from 'moment';

export const enum SystemStatus {
    GREEN = 'GREEN',
    AMBER = 'AMBER',
    RED = 'RED'
}

export interface IApp {
    id?: number;
    name?: string;
    problemCount?: number;
    sysStatus?: SystemStatus;
    lastProblemDate?: Moment;
}

export class App implements IApp {
    constructor(
        public id?: number,
        public name?: string,
        public problemCount?: number,
        public sysStatus?: SystemStatus,
        public lastProblemDate?: Moment
    ) {}
}
