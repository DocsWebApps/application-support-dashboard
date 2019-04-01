export interface IStats {
    p3Count?: number;
    p4Count?: number;
    problemCount?: string;
    riskCount?: string;
}

export class Stats implements IStats {
    constructor(public p3Count?: number, public p4Count?: number, public problemCount?: string, public riskCount?: string) {}
}
