(function(e){function t(t){for(var a,o,c=t[0],i=t[1],l=t[2],u=0,g=[];u<c.length;u++)o=c[u],Object.prototype.hasOwnProperty.call(r,o)&&r[o]&&g.push(r[o][0]),r[o]=0;for(a in i)Object.prototype.hasOwnProperty.call(i,a)&&(e[a]=i[a]);d&&d(t);while(g.length)g.shift()();return n.push.apply(n,l||[]),s()}function s(){for(var e,t=0;t<n.length;t++){for(var s=n[t],a=!0,o=1;o<s.length;o++){var i=s[o];0!==r[i]&&(a=!1)}a&&(n.splice(t--,1),e=c(c.s=s[0]))}return e}var a={},r={app:0},n=[];function o(e){return c.p+"static/js/"+({}[e]||e)+"."+{"chunk-2d0a4c1e":"497a6732","chunk-2d0aecf7":"5b2acb2f","chunk-2d0c85f8":"d0a9aeea","chunk-2d0d03d6":"b4f888d3"}[e]+".js"}function c(t){if(a[t])return a[t].exports;var s=a[t]={i:t,l:!1,exports:{}};return e[t].call(s.exports,s,s.exports,c),s.l=!0,s.exports}c.e=function(e){var t=[],s=r[e];if(0!==s)if(s)t.push(s[2]);else{var a=new Promise((function(t,a){s=r[e]=[t,a]}));t.push(s[2]=a);var n,i=document.createElement("script");i.charset="utf-8",i.timeout=120,c.nc&&i.setAttribute("nonce",c.nc),i.src=o(e);var l=new Error;n=function(t){i.onerror=i.onload=null,clearTimeout(u);var s=r[e];if(0!==s){if(s){var a=t&&("load"===t.type?"missing":t.type),n=t&&t.target&&t.target.src;l.message="Loading chunk "+e+" failed.\n("+a+": "+n+")",l.name="ChunkLoadError",l.type=a,l.request=n,s[1](l)}r[e]=void 0}};var u=setTimeout((function(){n({type:"timeout",target:i})}),12e4);i.onerror=i.onload=n,document.head.appendChild(i)}return Promise.all(t)},c.m=e,c.c=a,c.d=function(e,t,s){c.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:s})},c.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},c.t=function(e,t){if(1&t&&(e=c(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var s=Object.create(null);if(c.r(s),Object.defineProperty(s,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)c.d(s,a,function(t){return e[t]}.bind(null,a));return s},c.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return c.d(t,"a",t),t},c.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},c.p="/",c.oe=function(e){throw console.error(e),e};var i=window["webpackJsonp"]=window["webpackJsonp"]||[],l=i.push.bind(i);i.push=t,i=i.slice();for(var u=0;u<i.length;u++)t(i[u]);var d=l;n.push([0,"chunk-vendors"]),s()})({0:function(e,t,s){e.exports=s("56d7")},"1f57":function(e,t,s){"use strict";var a=s("7424");class r{getPublicContent(){return a["a"].get("/test/all")}getUserBoard(){return a["a"].get("/test/user")}getStaffBoard(){return a["a"].get("/test/staff")}getAdminBoard(){return a["a"].get("/test/admin")}getAll(){return a["a"].get("/admin/usermgmt/users")}get(e){return a["a"].get("/admin/usermgmt/users/"+e)}create(e){return a["a"].post("/admin/usermgmt/users",e)}update(e,t){return a["a"].put("/admin/usermgmt/users/"+e,t)}delete(e){return a["a"].delete("/admin/usermgmt/users/"+e)}}t["a"]=new r},"275e":function(e,t,s){"use strict";s("a845")},"30ef":function(e,t,s){"use strict";const a={on(e,t){document.addEventListener(e,e=>t(e.detail))},dispatch(e,t){document.dispatchEvent(new CustomEvent(e,{detail:t}))},remove(e,t){document.removeEventListener(e,t)}};t["a"]=a},"56d7":function(e,t,s){"use strict";s.r(t);var a=s("7a23");const r={id:"app"},n={class:"navbar navbar-expand navbar-dark bg-dark"},o=Object(a["g"])("a",{href:"/",class:"navbar-brand"},"KYUNGSEO.PoC",-1),c={class:"navbar-nav mr-auto"},i={class:"nav-item"},l={key:0,class:"nav-item"},u={key:1,class:"nav-item"},d={class:"nav-item"},g={key:0,class:"navbar-nav ml-auto"},b={class:"nav-item"},m={class:"nav-item"},j={key:1,class:"navbar-nav ml-auto"},p={class:"nav-item"},O={class:"nav-item"},f={class:"container"},h=Object(a["g"])("div",{id:"notification"},null,-1);function v(e,t,s,v,k,y){const S=Object(a["H"])("font-awesome-icon"),w=Object(a["H"])("router-link"),I=Object(a["H"])("toast"),T=Object(a["H"])("router-view");return Object(a["z"])(),Object(a["f"])("div",r,[Object(a["g"])("nav",n,[o,Object(a["g"])("div",c,[Object(a["g"])("li",i,[Object(a["j"])(w,{to:"/home",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["j"])(S,{icon:"home"}),Object(a["i"])(" Home ")]),_:1})]),y.showAdminBoard?(Object(a["z"])(),Object(a["f"])("li",l,[Object(a["j"])(w,{to:"/admin",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["i"])("Admin Board")]),_:1})])):Object(a["e"])("",!0),y.showStaffBoard?(Object(a["z"])(),Object(a["f"])("li",u,[Object(a["j"])(w,{to:"/staff",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["i"])("Staff Board")]),_:1})])):Object(a["e"])("",!0),Object(a["g"])("li",d,[y.currentUser?(Object(a["z"])(),Object(a["d"])(w,{key:0,to:"/user",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["i"])("User")]),_:1})):Object(a["e"])("",!0)])]),y.currentUser?Object(a["e"])("",!0):(Object(a["z"])(),Object(a["f"])("div",g,[Object(a["g"])("li",b,[Object(a["j"])(w,{to:"/register",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["j"])(S,{icon:"user-plus"}),Object(a["i"])(" Sign Up ")]),_:1})]),Object(a["g"])("li",m,[Object(a["j"])(w,{to:"/login",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["j"])(S,{icon:"sign-in-alt"}),Object(a["i"])(" Login ")]),_:1})])])),y.currentUser?(Object(a["z"])(),Object(a["f"])("div",j,[Object(a["g"])("li",p,[Object(a["j"])(w,{to:"/profile",class:"nav-link"},{default:Object(a["S"])(()=>[Object(a["j"])(S,{icon:"user"}),Object(a["i"])(" "+Object(a["K"])(y.currentUser.username),1)]),_:1})]),Object(a["g"])("li",O,[Object(a["g"])("a",{class:"nav-link",onClick:t[0]||(t[0]=Object(a["V"])((...e)=>y.logOut&&y.logOut(...e),["prevent"]))},[Object(a["j"])(S,{icon:"sign-out-alt"}),Object(a["i"])(" LogOut ")])])])):Object(a["e"])("",!0)]),Object(a["g"])("div",f,[Object(a["j"])(I),h,Object(a["g"])("em",null,Object(a["K"])(v.today),1),Object(a["j"])(T)])])}s("14d9");var k=s("30ef"),y=s("cf05"),S=s.n(y);const w={style:{position:"absolute",top:"60px",right:"10px"}},I={class:"toast-header"},T=Object(a["g"])("img",{src:S.a,class:"rounded me-2",alt:"Vue 3 데모",width:"15",height:"15"},null,-1),U=Object(a["g"])("strong",{class:"me-auto"},"Warning",-1),_=["onClick"],D={class:"toast-body"};function E(e,t,s,r,n,o){return Object(a["z"])(),Object(a["f"])("div",w,[(Object(a["z"])(!0),Object(a["f"])(a["a"],null,Object(a["F"])(r.toasts.data,e=>(Object(a["z"])(),Object(a["f"])("div",{key:e.id,class:"toast show text-white bg-danger",role:"alert"},[Object(a["g"])("div",I,[T,U,Object(a["g"])("button",{type:"button",class:"btn-close",onClick:t=>r.onClose(e.id)},null,8,_)]),Object(a["g"])("div",D,Object(a["K"])(e.message),1)]))),128))])}s("3c65");var P={setup(){const e=Object(a["o"])("toast",""),t=Object(a["D"])({data:[]});let s=0;Object(a["Q"])(()=>e,a=>{if(a.value.length>0){const r=s++;t.data.unshift({id:r,message:a.value,start:(new Date).getTime()}),e.value=""}},{deep:!0});const r=e=>{t.data=t.data.filter(t=>t.id!=e)};return Object(a["x"])(()=>{setInterval(()=>{const e=(new Date).getTime();t.data.forEach(s=>{e-s.start>5e3&&(t.data=t.data.filter(e=>e.id!=s.id))})})}),{toasts:t,onClose:r}}},A=s("6b0d"),L=s.n(A);const x=L()(P,[["render",E]]);var z=x,C={name:"App",setup(){const e=Object(a["E"])("");Object(a["B"])("toast",e);const t=Object(a["o"])("today");return{today:t}},components:{Toast:z},computed:{currentUser(){return this.$store.state.auth.user},showAdminBoard(){return!(!this.currentUser||!this.currentUser["roles"])&&this.currentUser["roles"].includes("ROLE_ADMIN")},showStaffBoard(){return!(!this.currentUser||!this.currentUser["roles"])&&this.currentUser["roles"].includes("ROLE_STAFF")}},methods:{logOut(){this.$store.dispatch("auth/logout").then(()=>{this.$router.push("/login")})}},mounted(){k["a"].on("logout",()=>{this.logOut()})},beforeUnmount(){k["a"].remove("logout")}};const F=L()(C,[["render",v]]);var N=F,H=s("6605");const R={class:"container"},B={class:"jumbotron"};function J(e,t,s,r,n,o){return Object(a["z"])(),Object(a["f"])("div",R,[Object(a["g"])("header",B,[Object(a["g"])("h3",null,Object(a["K"])(n.content),1)])])}var $=s("1f57"),q={name:"Home",data(){return{content:""}},mounted(){$["a"].getPublicContent().then(e=>{this.content=e.data},e=>{this.content=e.response&&e.response.data&&e.response.data.message||e.message||e.toString()})}};const M=L()(q,[["render",J]]);var K=M;const V=e=>(Object(a["C"])("data-v-3a75b22f"),e=e(),Object(a["A"])(),e),Y={class:"col-md-12"},G={class:"card card-container"},Q=V(()=>Object(a["g"])("img",{id:"profile-img",src:"//ssl.gstatic.com/accounts/ui/avatar_2x.png",class:"profile-img-card"},null,-1)),W={class:"form-group"},X=V(()=>Object(a["g"])("label",{for:"username"},"Username",-1)),Z={class:"form-group"},ee=V(()=>Object(a["g"])("label",{for:"password"},"Password",-1)),te={class:"form-group"},se=["disabled"],ae={class:"spinner-border spinner-border-sm"},re=V(()=>Object(a["g"])("span",null,"Login",-1)),ne={class:"form-group"},oe={key:0,class:"alert alert-danger",role:"alert"};function ce(e,t,s,r,n,o){const c=Object(a["H"])("Field"),i=Object(a["H"])("ErrorMessage"),l=Object(a["H"])("Form");return Object(a["z"])(),Object(a["f"])("div",Y,[Object(a["g"])("div",G,[Q,Object(a["j"])(l,{onSubmit:o.handleLogin,"validation-schema":n.schema},{default:Object(a["S"])(()=>[Object(a["g"])("div",W,[X,Object(a["j"])(c,{name:"username",type:"text",class:"form-control"}),Object(a["j"])(i,{name:"username",class:"error-feedback"})]),Object(a["g"])("div",Z,[ee,Object(a["j"])(c,{name:"password",type:"password",class:"form-control"}),Object(a["j"])(i,{name:"password",class:"error-feedback"})]),Object(a["g"])("div",te,[Object(a["g"])("button",{class:"btn btn-primary btn-block",disabled:n.loading},[Object(a["T"])(Object(a["g"])("span",ae,null,512),[[a["O"],n.loading]]),re],8,se)]),Object(a["g"])("div",ne,[n.message?(Object(a["z"])(),Object(a["f"])("div",oe,Object(a["K"])(n.message),1)):Object(a["e"])("",!0)])]),_:1},8,["onSubmit","validation-schema"])])])}var ie=s("7bb1"),le=s("506a"),ue={name:"Login",components:{Form:ie["c"],Field:ie["b"],ErrorMessage:ie["a"]},data(){const e=le["a"]().shape({username:le["b"]().required("Username is required!"),password:le["b"]().required("Password is required!")});return{loading:!1,message:"",schema:e}},computed:{loggedIn(){return this.$store.state.auth.status.loggedIn}},created(){this.loggedIn&&this.$router.push("/profile")},methods:{handleLogin(e){this.loading=!0,e.email=e.username,this.$store.dispatch("auth/login",e).then(()=>{this.$router.push("/profile")},e=>{this.loading=!1,this.message=e.response&&e.response.data&&e.response.data.message||e.message||e.toString()})}}};s("275e");const de=L()(ue,[["render",ce],["__scopeId","data-v-3a75b22f"]]);var ge=de;const be=e=>(Object(a["C"])("data-v-6bb4e539"),e=e(),Object(a["A"])(),e),me={class:"col-md-12"},je={class:"card card-container"},pe=be(()=>Object(a["g"])("img",{id:"profile-img",src:"//ssl.gstatic.com/accounts/ui/avatar_2x.png",class:"profile-img-card"},null,-1)),Oe={key:0},fe={class:"form-group"},he=be(()=>Object(a["g"])("label",{for:"username"},"Username",-1)),ve={class:"form-group"},ke=be(()=>Object(a["g"])("label",{for:"email"},"Email",-1)),ye={class:"form-group"},Se=be(()=>Object(a["g"])("label",{for:"password"},"Password",-1)),we={class:"form-group"},Ie=["disabled"],Te={class:"spinner-border spinner-border-sm"};function Ue(e,t,s,r,n,o){const c=Object(a["H"])("Field"),i=Object(a["H"])("ErrorMessage"),l=Object(a["H"])("Form");return Object(a["z"])(),Object(a["f"])("div",me,[Object(a["g"])("div",je,[pe,Object(a["j"])(l,{onSubmit:o.handleRegister,"validation-schema":n.schema},{default:Object(a["S"])(()=>[n.successful?Object(a["e"])("",!0):(Object(a["z"])(),Object(a["f"])("div",Oe,[Object(a["g"])("div",fe,[he,Object(a["j"])(c,{name:"username",type:"text",class:"form-control"}),Object(a["j"])(i,{name:"username",class:"error-feedback"})]),Object(a["g"])("div",ve,[ke,Object(a["j"])(c,{name:"email",type:"email",class:"form-control"}),Object(a["j"])(i,{name:"email",class:"error-feedback"})]),Object(a["g"])("div",ye,[Se,Object(a["j"])(c,{name:"password",type:"password",class:"form-control"}),Object(a["j"])(i,{name:"password",class:"error-feedback"})]),Object(a["g"])("div",we,[Object(a["g"])("button",{class:"btn btn-primary btn-block",disabled:n.loading},[Object(a["T"])(Object(a["g"])("span",Te,null,512),[[a["O"],n.loading]]),Object(a["i"])(" Sign Up ")],8,Ie)])]))]),_:1},8,["onSubmit","validation-schema"]),n.message?(Object(a["z"])(),Object(a["f"])("div",{key:0,class:Object(a["s"])(["alert",n.successful?"alert-success":"alert-danger"])},Object(a["K"])(n.message),3)):Object(a["e"])("",!0)])])}var _e={name:"Register",components:{Form:ie["c"],Field:ie["b"],ErrorMessage:ie["a"]},data(){const e=le["a"]().shape({username:le["b"]().required("사용자 ID는 필수 항목입니다.").min(3,"3 글자 이상이어야 합니다.").max(20,"20 글자를 초과할 수 없습니다."),email:le["b"]().required("이메일은 필수 항목입니다.").email("Email이 잘못되었습니다.").max(50,"50 글자를 초과할 수 없습니다."),password:le["b"]().required("비밀번호는 필수 항목입니다.").min(6,"6 글자 이상이어야 합니다.").max(40,"40 글자를 초과할 수 없습니다.")});return{successful:!1,loading:!1,message:"",schema:e}},computed:{loggedIn(){return this.$store.state.auth.status.loggedIn}},mounted(){this.loggedIn&&this.$router.push("/profile")},methods:{handleRegister(e){this.message="",this.successful=!1,this.loading=!0,this.$store.dispatch("auth/register",e).then(e=>{this.message=e.message,this.successful=!0,this.loading=!1},e=>{this.message=e.response&&e.response.data&&e.response.data.message||e.message||e.toString(),this.successful=!1,this.loading=!1})}}};s("ad3b");const De=L()(_e,[["render",Ue],["__scopeId","data-v-6bb4e539"]]);var Ee=De;const Pe=()=>s.e("chunk-2d0d03d6").then(s.bind(null,"66aa")),Ae=()=>s.e("chunk-2d0c85f8").then(s.bind(null,"5535")),Le=()=>s.e("chunk-2d0aecf7").then(s.bind(null,"0c3a")),xe=()=>s.e("chunk-2d0a4c1e").then(s.bind(null,"0899")),ze=Object(a["k"])({template:"<div>Not Found</div>"}),Ce=[{path:"/",redirect:"/home"},{path:"/home",component:K},{path:"/login",component:ge},{path:"/register",component:Ee},{path:"/profile",name:"profile",component:Pe},{path:"/admin",name:"admin",component:Ae},{path:"/staff",name:"staff",component:Le},{path:"/user",name:"user",component:xe},{path:"/:catchAll(.*)+",component:ze}],Fe=Object(H["a"])({history:Object(H["b"])(),linkActiveClass:"active",routes:Ce});var Ne=Fe,He=s("5502"),Re=s("7424");class Be{getLocalRefreshToken(){const e=JSON.parse(localStorage.getItem("user"));return null===e||void 0===e?void 0:e.refreshToken}getLocalAccessToken(){const e=JSON.parse(localStorage.getItem("user"));return null===e||void 0===e?void 0:e.accessToken}updateLocalAccessToken(e){let t=JSON.parse(localStorage.getItem("user"));t.accessToken=e,localStorage.setItem("user",JSON.stringify(t))}getUser(){return JSON.parse(localStorage.getItem("user"))}setUser(e){console.log(JSON.stringify(e)),localStorage.setItem("user",JSON.stringify(e))}removeUser(){localStorage.removeItem("user")}}var Je=new Be,$e=s("500b");class qe{getDeviceUUID(){let e=(new $e["DeviceUUID"]).get();return console.log("Device UUID: "+e),e}}var Me=new qe;class Ke{login(e){return e.deviceInfo={deviceId:Me.getDeviceUUID(),deviceType:"DEVICE_TYPE_ANDROID",notificationToken:"notificationToken"},Re["a"].post("/auth/login",e).then(e=>(console.log("Response: "+e),e.data.accessToken&&Je.setUser(e.data),e.data))}logout(){const e={};return e.token=Je.getLocalAccessToken(),e.deviceInfo={deviceId:Me.getDeviceUUID(),deviceType:"DEVICE_TYPE_ANDROID",notificationToken:"notificationToken"},Re["a"].post("/user/logout",e).then(e=>(console.log("Response: "+e),e.data.success&&Je.removeUser(),e.data))}register({username:e,email:t,password:s}){return Re["a"].post("/auth/register",{username:e,email:t,password:s})}}var Ve=new Ke;const Ye=JSON.parse(localStorage.getItem("user")),Ge=Ye?{status:{loggedIn:!0},user:Ye}:{status:{loggedIn:!1},user:null},Qe={namespaced:!0,state:Ge,actions:{login({commit:e},t){return Ve.login(t).then(t=>(e("loginSuccess",t),Promise.resolve(t)),t=>(e("loginFailure"),Promise.reject(t)))},logout({commit:e}){Ve.logout(),e("logout")},register({commit:e},t){return Ve.register(t).then(t=>(e("registerSuccess"),Promise.resolve(t.data)),t=>(e("registerFailure"),Promise.reject(t)))},refreshToken({commit:e},t){e("refreshToken",t)}},mutations:{loginSuccess(e,t){e.status.loggedIn=!0,e.user=t},loginFailure(e){e.status.loggedIn=!1,e.user=null},logout(e){e.status.loggedIn=!1,e.user=null},registerSuccess(e){e.status.loggedIn=!1},registerFailure(e){e.status.loggedIn=!1},refreshToken(e,t){e.status.loggedIn=!0,e.user={...e.user,accessToken:t}}},getters:{isLoggedIn:e=>e.status.loggedIn,getUserName:e=>e.user.getUserName}},We=Object(He["a"])({modules:{auth:Qe}});var Xe=We,Ze=(s("4989"),s("ab8b"),s("0449")),et=(s("4998"),s("ecee")),tt=s("ad3d"),st=s("c074");et["c"].add(st["a"],st["d"],st["e"],st["b"],st["c"]);const at=e=>{Re["a"].interceptors.request.use(e=>{const t=Je.getLocalAccessToken();return t&&(e.headers["Authorization"]="Bearer "+t),e},e=>Promise.reject(e)),Re["a"].interceptors.response.use(e=>e,async t=>{const s=t.config;if("/auth/login"!==s.url&&t.response&&401===t.response.status&&!s._retry){s._retry=!0;try{const t=await Re["a"].post("/auth/refresh",{refreshToken:Je.getLocalRefreshToken()}),{accessToken:a}=t.data;return e.dispatch("auth/refreshToken",a),Je.updateLocalAccessToken(a),Object(Re["a"])(s)}catch(a){return Promise.reject(a)}}return Promise.reject(t)})};var rt=at;rt(Xe);const nt=(new Date).getTimezoneOffset(),ot=new Date((new Date).getTime()-60*nt*1e3).toISOString().split("T")[0];Object(a["c"])(N).use(Ne).use(Xe).use(Ze["a"]).component("font-awesome-icon",tt["a"]).provide("today",ot).mount("#app")},"6eef":function(e,t,s){},7424:function(e,t,s){"use strict";var a=s("bc3a"),r=s.n(a);const n=r.a.create({baseURL:"http://localhost:8090/api/v1",headers:{"Content-Type":"application/json"}});t["a"]=n},a845:function(e,t,s){},ad3b:function(e,t,s){"use strict";s("6eef")},cf05:function(e,t,s){e.exports=s.p+"static/img/logo.82b9c7a5.png"}});
//# sourceMappingURL=app.a73a480c.js.map