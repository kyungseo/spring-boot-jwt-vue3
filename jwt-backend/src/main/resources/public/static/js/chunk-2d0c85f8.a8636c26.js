(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0c85f8"],{5535:function(e,s,t){"use strict";t.r(s);var a=t("7a23");const n={class:"container"};function o(e,s,t,o,l,r){const d=Object(a["H"])("vue-good-table");return Object(a["z"])(),Object(a["f"])("div",n,[Object(a["j"])(d,{columns:l.columns,rows:l.rows},null,8,["columns","rows"])])}var l=t("1f57"),r=t("30ef"),d={name:"Admin",data(){return{columns:[{label:"Name",field:"membername"},{label:"Email",field:"email"},{label:"Birthdate",field:"birthdate"},{label:"Phone Number",field:"phoneNumber"}],rows:[]}},methods:{getAllUsers(){const e=Object(a["o"])("toast","");l["a"].getAll().then(e=>{this.rows=e.data.data.dtos},s=>{e.value=s.response&&s.response.data&&s.response.data.message||s.message||s.toString(),s.response&&403===s.response.status&&r["a"].dispatch("logout")})}},mounted(){this.getAllUsers()}},c=t("6b0d"),i=t.n(c);const u=i()(d,[["render",o]]);s["default"]=u}}]);
//# sourceMappingURL=chunk-2d0c85f8.a8636c26.js.map