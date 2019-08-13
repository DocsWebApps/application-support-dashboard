import { Moment } from 'moment';
import { IProblem } from 'app/shared/model/problem.model';
import { IRiskUpdates } from 'app/shared/model/risk-updates.model';

export const enum IssueStatus {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED'
}

export const enum Priority {
    LOW = 'LOW',
    MEDIUM = 'MEDIUM',
    HIGH = 'HIGH'
}

export interface IRisk {
    id?: number;
    openedAt?: Moment;
    title?: string;
    description?: any;
    mitigation?: any;
    riskStatus?: IssueStatus;
    priority?: Priority;
    closedAt?: Moment;
    problems?: IProblem[];
    riskUpdates?: IRiskUpdates[];
    problemCount?: number;
}

export class Risk implements IRisk {
    constructor(
        public id?: number,
        public openedAt?: Moment,
        public title?: string,
        public description?: any,
        public mitigation?: any,
        public riskStatus?: IssueStatus,
        public priority?: Priority,
        public closedAt?: Moment,
        public problems?: IProblem[],
        public riskUpdates?: IRiskUpdates[],
        public problemCount?: number
    ) {}
}
