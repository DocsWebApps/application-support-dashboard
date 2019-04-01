import { Component, OnInit } from '@angular/core';
import { Name } from 'app/shared/model/name.model.ts';
import { NameService } from 'app/shared/services/name.service.ts';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
    date = new Date();
    dashName: String;

    constructor(private nameService: NameService) {}

    ngOnInit() {
        this.getDashName();
    }

    getDashName() {
        this.nameService.getName().subscribe((res: HttpResponse<Name>) => {
            this.dashName = res.body.name;
        });
    }

    getYear() {
        return String(this.date.getFullYear());
    }
}
