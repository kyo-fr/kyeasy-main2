import { getapi,postapi,putapi,delapi } from "../uitls/http";

// getFieldtype 获取字段类型
export const getFieldtype = (data) => getapi("/ares-gen/fieldtype/page", data);
export const getAllFiledType = () => getapi("/ares-gen/fieldtype/all")
export const getFiledMapping = () => getapi("/ares-gen/fieldtype/mapping")

//项目
export const getProject = (data) => getapi("/ares-gen/project/page", data);
export const addProject = (data) => postapi("/ares-gen/project", data);
export const updateProject = (data) => putapi("/ares-gen/project", data);
export const delProject = (data) => delapi(`/ares-gen/project`,data);

// 数据源
export const getDataSource = (data) => getapi("/ares-gen/datasource/page", data);
export const addDataSource = (data) => postapi("/ares-gen/datasource", data);
export const updateDataSource = (data) => putapi("/ares-gen/datasource", data);
export const delDataSource = (data) => delapi(`/ares-gen/datasource`,data);
export const testDataSource = (data) => getapi(`/ares-gen/datasource/test/${data}`);
export const getAllList = () => getapi("/ares-gen/datasource/list");
export const getDataSourceTableList = (data) => getapi(`/ares-gen/datasource/table/list/${data}`);

//基础类
export const getBaseClass = (data) => getapi("/ares-gen/baseclass/page", data);
export const addBaseClass = (data) => postapi("/ares-gen/baseclass", data);
export const updateBaseClass = (data) => putapi("/ares-gen/baseclass", data);
export const delBaseClass = (data) => delapi(`/ares-gen/baseclass`,data);
export const listBaseClass = () =>getapi("/ares-gen/baseclass/list");
//模型
export const getTable = (data) => getapi("/ares-gen/model/page", data);
export const addModel = (data) => postapi("/ares-gen/model", data);
export const updateModel = (data) => putapi("/ares-gen/model", data);
export const delTable = (data) => delapi(`/ares-gen/model`,data);
export const getTableDetails= (id) => getapi(`/ares-gen/model/${id}`);
export const importTable = (id,data) => postapi(`/ares-gen/model/import/${id}`, data);
export const addField = (id,data) => postapi(`/ares-gen/model/field/${id}`,data);
export const updateField = (id,data) => putapi(`/ares-gen/model/field/${id}`,data);
export const delField = (ids) => delapi(`/ares-gen/model/field`,ids);
export const getAllBaseModel = () => getapi("/ares-gen/model/get_all_base_model")
export const importBaseModelFiled = (modelId,baseModelId) =>postapi(`ares-gen/model/import_base_model_filed`,{modelId,baseModelId})
//
export const GenCode = (id) => postapi("/ares-gen/generator/code", [id]);

//根据模型生成
export const ModelGenCode = (data) => postapi("/ares-gen/generator/model_gen", data);