/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Data } from '@angular/router';
import { ApplicationSupportDashboardTestModule } from '../../../test.module';
import { BannerComponent } from 'app/home/banner/banner.component';
import { BannerService } from 'app/home/banner/banner.service';
import { StatusService } from 'app/home/status/status.service';
import { SystemStatus } from 'app/shared/model/app.model';

describe('Component Tests', () => {
    describe('Banner Stats Component', () => {
        let comp: BannerComponent;
        let fixture: ComponentFixture<BannerComponent>;
        let service: BannerService;
        let service1: StatusService;
        // let EventEmitter = require('events').EventEmitter;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApplicationSupportDashboardTestModule],
                declarations: [BannerComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            data: {
                                subscribe: (fn: (value: Data) => void) =>
                                    fn({
                                        pagingParams: {
                                            predicate: 'id',
                                            reverse: false,
                                            page: 0
                                        }
                                    })
                            }
                        }
                    }
                ]
            })
                .overrideTemplate(BannerComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BannerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BannerService);
            service1 = fixture.debugElement.injector.get(StatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'getBannerStats');

            // WHEN
            comp.ngOnInit();
            service1.incidentSwitch.emit(SystemStatus.GREEN);

            // // THEN
            // expect(service.getBannerStats).toHaveBeenCalled();
        });
        /*
        var sinon = require('sinon');
var EventEmitter = require('events').EventEmitter;

describe('EventEmitter', function(){
  describe('#emit()', function(){
    it('should invoke the callback', function(){
      var spy = sinon.spy();
      var emitter = new EventEmitter;

      emitter.on('foo', spy);
      emitter.emit('foo');
      spy.called.should.equal.true;
    })

    it('should pass arguments to the callbacks', function(){
      var spy = sinon.spy();
      var emitter = new EventEmitter;

      emitter.on('foo', spy);
      emitter.emit('foo', 'bar', 'baz');
      sinon.assert.calledOnce(spy);
      sinon.assert.calledWith(spy, 'bar', 'baz');
    })
  })
})
         */
    });
});
