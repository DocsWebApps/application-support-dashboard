import { Moment } from 'moment';
import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';

export const enum Severity {
    P1 = 'P1',
    P2 = 'P2',
    P3 = 'P3',
    P4 = 'P4'
}

export const enum IssueStatus {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED'
}

export interface IIncident {
    id?: number;
    openedAt?: Moment;
    description?: string;
    severity?: Severity;
    incidentStatus?: IssueStatus;
    closedAt?: Moment;
    incidentUpdates?: IIncidentUpdates[];
    probRecId?: number;
}

export class Incident implements IIncident {
    constructor(
        public id?: number,
        public openedAt?: Moment,
        public description?: string,
        public severity?: Severity,
        public incidentStatus?: IssueStatus,
        public closedAt?: Moment,
        public incidentUpdates?: IIncidentUpdates[],
        public probRecId?: number
    ) {}
}
