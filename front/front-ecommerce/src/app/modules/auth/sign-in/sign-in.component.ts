import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'app/core/auth/auth.service';
// import { fuseAnimations } from '@fuse/animations';
// import { FuseAlertType } from '@fuse/components/alert';


@Component({
    selector     : 'auth-sign-in',
    templateUrl  : './sign-in.component.html',
    encapsulation: ViewEncapsulation.None,
    // animations   : fuseAnimations
})
export class SignInComponent implements OnInit
{
    @ViewChild('signInNgForm') signInNgForm: NgForm;

    // alert: { type: FuseAlertType; message: string } = {
    //     type   : 'success',
    //     message: ''
    // };
    signInForm: UntypedFormGroup;
    showAlert: boolean = false;

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private authService: AuthService,
        private _formBuilder: UntypedFormBuilder,
        private _router: Router
    )
    {}
    /**
     * On init
     */
    ngOnInit(): void
    {
        // Create the form
        this.signInForm = this._formBuilder.group({
            email     : ['', [Validators.required, Validators.email]],
            password  : ['', Validators.required],
            rememberMe: ['']
        });
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Sign in
     */
    signIn(): void
    {
        // Return if the form is invalid
        if ( this.signInForm.invalid )
        {
            return;
        }
        const {email, password} = this.signInForm.value;
        const credentials = 
        {
          email,
          password
        }
        this.authService.signIn(credentials)
        .subscribe({
          next: response => {
            console.log("Loggeado gonorrea  => ", response);
            this.authService.accessToken = response.token;
            this._router.navigate(['category'])
          },
          error: err => {
            console.log("error => ", err)
          }
        })
    }
}
