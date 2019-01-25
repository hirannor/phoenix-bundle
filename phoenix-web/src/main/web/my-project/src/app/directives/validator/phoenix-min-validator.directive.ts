import {Directive, Input} from '@angular/core';
import {FormControl, NG_VALIDATORS, Validator} from '@angular/forms';

@Directive({
  selector: '[min][formControlName],[min][formControl],[min][ngModel]',
  providers: [{provide: NG_VALIDATORS, useExisting: PhoenixMinDirective, multi: true}]
})
export class PhoenixMinDirective implements Validator {
  @Input()
  min: number;

  validate(c: FormControl): {[key: string]: any} {
    let v = c.value;
    return ( v < this.min)? {"min": true} : null;
  }
}
