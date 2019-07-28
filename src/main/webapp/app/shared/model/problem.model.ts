import { Moment } from 'moment';
import { IIncident } from 'app/shared/model/incident.model';
import { IProblemUpdates } from 'app/shared/model/problem-updates.model';

export const enum IssueStatus {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED'
}

export const enum Priority {
    LOW = 'LOW',
    MEDIUM = 'MEDIUM',
    HIGH = 'HIGH'
}

export interface IProblem {
    id?: number;
    openedAt?: Moment;
    title?: string;
    statement?: any;
    probStatus?: IssueStatus;
    priority?: Priority;
    closedAt?: Moment;
    incidents?: IIncident[];
    problemUpdates?: IProblemUpdates[];
    riskRecId?: number;
    incidentCount?: number;
}

export class Problem implements IProblem {
    constructor(
        public id?: number,
        public openedAt?: Moment,
        public title?: string,
        public statement?: any,
        public probStatus?: IssueStatus,
        public priority?: Priority,
        public closedAt?: Moment,
        public incidents?: IIncident[],
        public problemUpdates?: IProblemUpdates[],
        public riskRecId?: number,
        public incidentCount?: number
    ) {}
}
