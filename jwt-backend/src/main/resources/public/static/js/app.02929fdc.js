(function(e){function t(t){for(var r,o,a=t[0],u=t[1],l=t[2],b=0,d=[];b<a.length;b++)o=a[b],Object.prototype.hasOwnProperty.call(c,o)&&c[o]&&d.push(c[o][0]),c[o]=0;for(r in u)Object.prototype.hasOwnProperty.call(u,r)&&(e[r]=u[r]);i&&i(t);while(d.length)d.shift()();return s.push.apply(s,l||[]),n()}function n(){for(var e,t=0;t<s.length;t++){for(var n=s[t],r=!0,a=1;a<n.length;a++){var u=n[a];0!==c[u]&&(r=!1)}r&&(s.splice(t--,1),e=o(o.s=n[0]))}return e}var r={},c={app:0},s=[];function o(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,o),n.l=!0,n.exports}o.m=e,o.c=r,o.d=function(e,t,n){o.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},o.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.t=function(e,t){if(1&t&&(e=o(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(o.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)o.d(n,r,function(t){return e[t]}.bind(null,r));return n},o.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(t,"a",t),t},o.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},o.p="/";var a=window["webpackJsonp"]=window["webpackJsonp"]||[],u=a.push.bind(a);a.push=t,a=a.slice();for(var l=0;l<a.length;l++)t(a[l]);var i=u;s.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("cd49")},"1c23":function(e,t,n){"use strict";n("560d")},"1cd9":function(e,t,n){},"560d":function(e,t,n){},"813b":function(e,t,n){},ad5f:function(e,t,n){e.exports=n.p+"static/img/spring-boot-vuejs-logo.00da5c74.png"},cc6b:function(e,t,n){"use strict";n("f2c6")},cd49:function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("7a23"),c={id:"nav"};function s(e,t){var n=Object(r["y"])("router-link"),s=Object(r["y"])("router-view");return Object(r["r"])(),Object(r["e"])(r["a"],null,[Object(r["f"])("div",c,[Object(r["h"])(n,{to:"/"},{default:Object(r["E"])((function(){return[Object(r["g"])("Hello")]})),_:1}),Object(r["g"])(" | "),Object(r["h"])(n,{to:"/callservice"},{default:Object(r["E"])((function(){return[Object(r["g"])("Service")]})),_:1}),Object(r["g"])(" | "),Object(r["h"])(n,{to:"/bootstrap"},{default:Object(r["E"])((function(){return[Object(r["g"])("Bootstrap")]})),_:1}),Object(r["g"])(" | "),Object(r["h"])(n,{to:"/user"},{default:Object(r["E"])((function(){return[Object(r["g"])("User")]})),_:1}),Object(r["g"])(" | "),Object(r["h"])(n,{to:"/login"},{default:Object(r["E"])((function(){return[Object(r["g"])("Login")]})),_:1}),Object(r["g"])(" | "),Object(r["h"])(n,{to:"/protected"},{default:Object(r["E"])((function(){return[Object(r["g"])("Protected")]})),_:1})]),Object(r["h"])(s)],64)}n("ecfb");var o=n("6b0d"),a=n.n(o);const u={},l=a()(u,[["render",s]]);var i=l,b=(n("d3b7"),n("6605")),d=n("ad5f"),f=n.n(d),p={class:"home"},j=Object(r["f"])("img",{alt:"Vue with Spring logo",src:f.a},null,-1);function O(e,t,n,c,s,o){var a=Object(r["y"])("HelloSpringWorld");return Object(r["r"])(),Object(r["e"])("div",p,[j,Object(r["h"])(a,{hellomsg:"Welcome to your Vue.js (+ TypeScript) powered Spring Boot App"})])}var h=function(e){return Object(r["u"])("data-v-80ab084a"),e=e(),Object(r["s"])(),e},g={class:"hello"},v=h((function(){return Object(r["f"])("h2",null,"See the sources here: ",-1)})),m=h((function(){return Object(r["f"])("ul",null,[Object(r["f"])("li",null,[Object(r["f"])("a",{href:"https://github.com/jonashackt/spring-boot-vuejs",target:"_blank"},"github.com/jonashackt/spring-boot-vuejs")])],-1)})),y=h((function(){return Object(r["f"])("h3",null,"This site contains more stuff :)",-1)})),w=h((function(){return Object(r["f"])("li",null,"HowTo call REST-Services:",-1)})),S=h((function(){return Object(r["f"])("li",null,"HowTo to play around with Bootstrap UI components:",-1)})),k=h((function(){return Object(r["f"])("li",null,"HowTo to interact with the Spring Boot database backend:",-1)})),R=h((function(){return Object(r["f"])("li",null,"Login to the secured part of the application",-1)})),A=h((function(){return Object(r["f"])("li",null,"A secured part of this application:",-1)}));function U(e,t,n,c,s,o){var a=Object(r["y"])("router-link");return Object(r["r"])(),Object(r["e"])("div",g,[Object(r["f"])("h1",null,Object(r["A"])(n.hellomsg),1),v,m,y,Object(r["f"])("ul",null,[w,Object(r["f"])("li",null,[Object(r["h"])(a,{to:"/callservice"},{default:Object(r["E"])((function(){return[Object(r["g"])("/callservice")]})),_:1})]),S,Object(r["f"])("li",null,[Object(r["h"])(a,{to:"/bootstrap"},{default:Object(r["E"])((function(){return[Object(r["g"])("/bootstrap")]})),_:1})]),k,Object(r["f"])("li",null,[Object(r["h"])(a,{to:"/user"},{default:Object(r["E"])((function(){return[Object(r["g"])("/user")]})),_:1})]),R,Object(r["f"])("li",null,[Object(r["h"])(a,{to:"/login"},{default:Object(r["E"])((function(){return[Object(r["g"])("/login")]})),_:1})]),A,Object(r["f"])("li",null,[Object(r["h"])(a,{to:"/protected"},{default:Object(r["E"])((function(){return[Object(r["g"])("/protected")]})),_:1})])])])}var C={name:"HelloSpringWorld",props:{hellomsg:{type:String,required:!0}}};n("cd65");const _=a()(C,[["render",U],["__scopeId","data-v-80ab084a"]]);var E=_,T=Object(r["i"])({name:"Home",components:{HelloSpringWorld:E}});const N=a()(T,[["render",O]]);var P=N,H=function(e){return Object(r["u"])("data-v-cddfe2c0"),e=e(),Object(r["s"])(),e},I={class:"service"},x=H((function(){return Object(r["f"])("h2",null,"REST service call results",-1)}));function L(e,t,n,c,s,o){return Object(r["r"])(),Object(r["e"])("div",I,[Object(r["f"])("h1",null,Object(r["A"])(e.msg),1),x,Object(r["f"])("button",{onClick:t[0]||(t[0]=function(t){return e.callHelloApi()})},"CALL Spring Boot REST backend service"),Object(r["f"])("h4",null,"Backend response: "+Object(r["A"])(e.backendResponse),1)])}n("14d9");var B=n("bc3a"),F=n.n(B),V=F.a.create({baseURL:"/api",timeout:1e3,headers:{"Content-Type":"application/json"}}),M={hello:function(){return V.get("/hello")},getUser:function(e){return V.get("/user/"+e)},createUser:function(e,t){return V.post("/user/"+e+"/"+t)},getSecured:function(e,t){return V.get("/secured/",{auth:{username:e,password:t}})}},q=Object(r["i"])({name:"Service",data:function(){return{msg:"HowTo call REST-Services:",backendResponse:"",errors:[]}},methods:{callHelloApi:function(){var e=this;M.hello().then((function(t){e.backendResponse=t.data,console.log(t.data)})).catch((function(t){e.errors.push(t)}))}}});n("cc6b");const W=a()(q,[["render",L],["__scopeId","data-v-cddfe2c0"]]);var D=W,J=function(e){return Object(r["u"])("data-v-dd18674e"),e=e(),Object(r["s"])(),e},Y={class:"bootstrap"},G=J((function(){return Object(r["f"])("h5",null,"REST service call are easy to do with Vue.js, if you know how to do it.",-1)})),$=J((function(){return Object(r["f"])("p",null,null,-1)})),z=J((function(){return Object(r["f"])("h6",null,[Object(r["f"])("span",{class:"badge bg-primary"}," Let´s go!"),Object(r["g"])(" Call a Spring Boot REST backend service, by clicking a button:")],-1)})),K=J((function(){return Object(r["f"])("p",null,null,-1)})),Q=J((function(){return Object(r["f"])("p",null,null,-1)})),X=["show"],Z=J((function(){return Object(r["f"])("button",{class:"btn btn-secondary","data-bs-toggle":"collapse","data-bs-target":"#collapseOuter"},"Show Response details",-1)})),ee=J((function(){return Object(r["f"])("p",null,null,-1)})),te={class:"collapse",id:"collapseOuter"},ne={class:"card card-body"},re=J((function(){return Object(r["f"])("button",{class:"btn btn-primary","data-bs-toggle":"collapse","data-bs-target":"#collapseInnerStatusCode"},"HTTP Status",-1)})),ce={class:"collapse",id:"collapseInnerStatusCode"},se={class:"card card-body"},oe={class:"card card-body"},ae=J((function(){return Object(r["f"])("button",{class:"btn btn-warning","data-bs-toggle":"collapse","data-bs-target":"#collapseInnerHeaders"},"HTTP Headers",-1)})),ue={class:"collapse",id:"collapseInnerHeaders"},le={key:0},ie={class:"card card-body"},be=J((function(){return Object(r["f"])("button",{class:"btn btn-danger","data-bs-toggle":"collapse","data-bs-target":"#collapseInnerResponseConfig"},"Full Request configuration",-1)})),de={class:"collapse",id:"collapseInnerResponseConfig"},fe={class:"card card-body"};function pe(e,t,n,c,s,o){return Object(r["r"])(),Object(r["e"])("div",Y,[Object(r["f"])("h1",null,Object(r["A"])(e.msg),1),G,$,z,K,Object(r["f"])("button",{class:"btn btn-success",onClick:t[0]||(t[0]=function(t){return e.callHelloApi()}),id:"btnCallHello"},"/hello (GET)"),Q,Object(r["f"])("h4",null,[Object(r["g"])("Backend response: "),Object(r["f"])("span",{class:"alert alert-primary",role:"alert",show:e.showResponse,dismissible:"",onDismissed:t[1]||(t[1]=function(t){return e.showResponse=!1})},Object(r["A"])(e.backendResponse),41,X)]),Z,ee,Object(r["f"])("div",te,[Object(r["f"])("div",ne,[Object(r["g"])(" The Response hat this details "),re,Object(r["f"])("div",ce,[Object(r["f"])("div",se,"Status: "+Object(r["A"])(e.httpStatusCode),1),Object(r["f"])("div",oe,"Statustext: "+Object(r["A"])(e.httpStatusText),1)]),ae,Object(r["f"])("div",ue,[e.headers&&e.headers.length?(Object(r["r"])(),Object(r["e"])("p",le,[(Object(r["r"])(!0),Object(r["e"])(r["a"],null,Object(r["x"])(e.headers,(function(e){return Object(r["r"])(),Object(r["e"])("li",null,[Object(r["f"])("div",ie,"Header: "+Object(r["A"])(e.valueOf()),1)])})),256))])):Object(r["d"])("",!0)]),be,Object(r["f"])("div",de,[Object(r["f"])("div",fe,"Config: "+Object(r["A"])(e.responseConfig),1)])])])])}var je=Object(r["i"])({name:"Bootstrap",data:function(){return{msg:"Nice Bootstrap candy!",showResponse:!1,backendResponse:"",responseConfig:"",httpStatusCode:0,httpStatusText:"",headers:["Noting here atm. Did you call the Service?"],errors:[]}},methods:{callHelloApi:function(){var e=this;M.hello().then((function(t){e.backendResponse=t.data,e.httpStatusCode=t.status,e.httpStatusText=t.statusText,e.headers=t.headers,e.responseConfig=t.config,e.showResponse=!0})).catch((function(t){e.errors.push(t)}))}}});n("1c23");const Oe=a()(je,[["render",pe],["__scopeId","data-v-dd18674e"]]);var he=Oe,ge=function(e){return Object(r["u"])("data-v-ab1c889c"),e=e(),Object(r["s"])(),e},ve={class:"user"},me=ge((function(){return Object(r["f"])("h1",null,"Create User",-1)})),ye=ge((function(){return Object(r["f"])("h3",null,"Just some database interaction...",-1)})),we={key:0},Se={key:2};function ke(e,t,n,c,s,o){return Object(r["r"])(),Object(r["e"])("div",ve,[me,ye,Object(r["F"])(Object(r["f"])("input",{type:"text","onUpdate:modelValue":t[0]||(t[0]=function(t){return e.user.firstName=t}),placeholder:"first name"},null,512),[[r["C"],e.user.firstName]]),Object(r["F"])(Object(r["f"])("input",{type:"text","onUpdate:modelValue":t[1]||(t[1]=function(t){return e.user.lastName=t}),placeholder:"last name"},null,512),[[r["C"],e.user.lastName]]),Object(r["f"])("button",{onClick:t[2]||(t[2]=function(t){return e.createNewUser()})},"Create User"),e.showResponse?(Object(r["r"])(),Object(r["e"])("div",we,[Object(r["f"])("h6",null,"User created with Id: "+Object(r["A"])(e.user.id),1)])):Object(r["d"])("",!0),e.showResponse?(Object(r["r"])(),Object(r["e"])("button",{key:1,onClick:t[3]||(t[3]=function(t){return e.retrieveUser()})},"Retrieve user "+Object(r["A"])(e.user.id)+" data from database",1)):Object(r["d"])("",!0),e.showRetrievedUser?(Object(r["r"])(),Object(r["e"])("h4",Se,"Retrieved User "+Object(r["A"])(e.retrievedUser.firstName)+" "+Object(r["A"])(e.retrievedUser.lastName),1)):Object(r["d"])("",!0)])}var Re=Object(r["i"])({name:"User",data:function(){return{errors:[],user:{id:0,firstName:"",lastName:""},showResponse:!1,retrievedUser:{id:0,firstName:"",lastName:""},showRetrievedUser:!1}},methods:{createNewUser:function(){var e=this;M.createUser(this.user.firstName,this.user.lastName).then((function(t){e.user.id=t.data,console.log("Created new User with Id "+t.data),e.showResponse=!0})).catch((function(t){e.errors.push(t)}))},retrieveUser:function(){var e=this;M.getUser(this.user.id).then((function(t){e.retrievedUser=t.data,e.showRetrievedUser=!0})).catch((function(t){e.errors.push(t)}))}}});n("ffab");const Ae=a()(Re,[["render",ke],["__scopeId","data-v-ab1c889c"]]);var Ue=Ae,Ce={key:0,class:"unprotected"},_e=Object(r["f"])("h1",null,[Object(r["f"])("span",{class:"badge bg-danger"},"You don't have rights here, mate :D")],-1),Ee=Object(r["f"])("h5",null,"Seems that you don't have access rights... ",-1),Te=[_e,Ee],Ne={key:1,class:"unprotected"},Pe=Object(r["f"])("h1",null,[Object(r["f"])("span",{class:"badge bg-warning text-dark"},"Please login to get access!")],-1),He=Object(r["f"])("h5",null,"You're not logged in - so you don't see much here. Try to log in:",-1),Ie=Object(r["f"])("button",{type:"submit",class:"btn btn-primary"},"Login",-1),xe={key:0,class:"error"};function Le(e,t,n,c,s,o){return e.loginError?(Object(r["r"])(),Object(r["e"])("div",Ce,Te)):(Object(r["r"])(),Object(r["e"])("div",Ne,[Pe,He,Object(r["f"])("form",{onSubmit:t[2]||(t[2]=Object(r["G"])((function(t){return e.callLogin()}),["prevent"]))},[Object(r["F"])(Object(r["f"])("input",{type:"text",placeholder:"username","onUpdate:modelValue":t[0]||(t[0]=function(t){return e.user=t})},null,512),[[r["C"],e.user]]),Object(r["F"])(Object(r["f"])("input",{type:"password",placeholder:"password","onUpdate:modelValue":t[1]||(t[1]=function(t){return e.password=t})},null,512),[[r["C"],e.password]]),Ie,e.error?(Object(r["r"])(),Object(r["e"])("p",xe,"Bad login information")):Object(r["d"])("",!0)],32)]))}var Be=Object(r["i"])({name:"Login",data:function(){return{loginError:!1,user:"",password:"",error:!1,errors:[]}},methods:{callLogin:function(){var e=this;this.errors=[],this.$store.dispatch("login",{user:this.user,password:this.password}).then((function(){e.$router.push("/Protected")})).catch((function(t){e.loginError=!0,e.errors.push(t),e.error=!0}))}}});const Fe=a()(Be,[["render",Le]]);var Ve=Fe,Me=Object(r["f"])("h1",null,[Object(r["f"])("span",{class:"badge bg-success"},"YEAH you made it!")],-1),qe=Object(r["f"])("h5",null,"If you're able to read this, you've successfully logged in and redirected to this protected site :)",-1),We=Object(r["f"])("p",null,null,-1),De={key:0},Je=Object(r["f"])("span",{class:"badge bg-success"},"API call",-1),Ye=Object(r["f"])("span",{class:"badge bg-success"},"successful",-1),Ge={key:1},$e=Object(r["f"])("span",{class:"badge bg-warning"},"API call",-1),ze=Object(r["f"])("span",{class:"badge bg-warning"},"NOT successful",-1);function Ke(e,t,n,c,s,o){return Object(r["r"])(),Object(r["e"])("div",null,[Me,qe,Object(r["f"])("button",{class:"btn btn-primary",onClick:t[0]||(t[0]=function(t){return e.getSecuredTextFromBackend()})},"Call the secured API"),We,e.securedApiCallSuccess?(Object(r["r"])(),Object(r["e"])("div",De,[Je,Object(r["g"])(" Full response: "+Object(r["A"])(e.backendResponse)+" ",1),Ye])):Object(r["d"])("",!0),e.errors?(Object(r["r"])(),Object(r["e"])("div",Ge,[$e,Object(r["g"])(" "+Object(r["A"])(e.errors)+" ",1),ze])):Object(r["d"])("",!0)])}var Qe=n("5502"),Xe=Object(Qe["a"])({state:{loginSuccess:!1,loginError:!1,userName:null,userPass:null},mutations:{login_success:function(e,t){e.loginSuccess=!0,e.userName=t.userName,e.userPass=t.userPass},login_error:function(e,t){e.loginError=!0,e.userName=t.userName}},actions:{login:function(e,t){var n=e.commit,r=t.user,c=t.password;return new Promise((function(e,t){console.log("Accessing backend with user: '"+r),M.getSecured(r,c).then((function(t){console.log("Response: '"+t.data+"' with Statuscode "+t.status),200==t.status&&(console.log("Login successful"),n("login_success",{userName:r,userPass:c})),e(t)})).catch((function(e){console.log("Error: "+e),n("login_error",{userName:r}),t("Invalid credentials!")}))}))}},getters:{isLoggedIn:function(e){return e.loginSuccess},hasLoginErrored:function(e){return e.loginError},getUserName:function(e){return e.userName},getUserPass:function(e){return e.userPass}}}),Ze=Object(r["i"])({name:"Protected",data:function(){return{backendResponse:"",securedApiCallSuccess:!1,errors:[]}},methods:{getSecuredTextFromBackend:function(){var e=this;M.getSecured(Xe.getters.getUserName,Xe.getters.getUserPass).then((function(t){console.log("Response: '"+t.data+"' with Statuscode "+t.status),e.securedApiCallSuccess=!0,e.backendResponse=t.data})).catch((function(t){console.log("Error: "+t),e.errors.push(t)}))}}});const et=a()(Ze,[["render",Ke]]);var tt=et,nt=[{path:"/",component:P},{path:"/callservice",component:D},{path:"/bootstrap",component:he},{path:"/user",component:Ue},{path:"/login",component:Ve},{path:"/protected",component:tt,meta:{requiresAuth:!0}},{path:"/:pathMatch(.*)*",redirect:"/"}],rt=Object(b["a"])({history:Object(b["b"])("/"),routes:nt});rt.beforeEach((function(e,t,n){e.matched.some((function(e){return e.meta.requiresAuth}))?Xe.getters.isLoggedIn?n():n({path:"/login"}):n()}));var ct=rt;n("ab8b"),n("7b17");Object(r["c"])(i).use(ct).use(Xe).mount("#app")},cd65:function(e,t,n){"use strict";n("813b")},cf4e:function(e,t,n){},ecfb:function(e,t,n){"use strict";n("1cd9")},f2c6:function(e,t,n){},ffab:function(e,t,n){"use strict";n("cf4e")}});
//# sourceMappingURL=app.02929fdc.js.map