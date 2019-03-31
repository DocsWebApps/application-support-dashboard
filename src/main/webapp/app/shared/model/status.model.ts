import { SystemStatus } from 'app/shared/model/app.model';

export interface IAppStatus {
    sysMessage?: string;
    sysMessageColor?: string;
    msgColor?: string;
    msgDetail?: string;
    appStatus?: SystemStatus;
    probCount?: string;
}

export class AppStatus implements IAppStatus {
    constructor(
        public sysMessage?: string,
        public sysMessageColor?: string,
        public msgColor?: string,
        public msgDetail?: string,
        public appStatus?: SystemStatus,
        public probCount?: string
    ) {}
}
