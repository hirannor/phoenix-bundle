import {Directive, Input} from '@angular/core';
import {FormControl, NG_VALIDATORS, Validator} from '@angular/forms';

@Directive({
  selector: '[max][formControlName],[max][formControl],[min][ngModel]',
  providers: [{provide: NG_VALIDATORS, useExisting: PhoenixMaxDirective, multi: true}]
})
export class PhoenixMaxDirective implements Validator {
  @Input()
  max: number;

  validate(c: FormControl): {[key: string]: any} {
    let v = c.value;
    return ( v > this.max)? {"max": true} : null;
  }
}
