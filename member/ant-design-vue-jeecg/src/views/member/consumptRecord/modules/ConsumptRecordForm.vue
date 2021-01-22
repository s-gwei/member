<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24" v-show=false >
            <a-form-item label="会员id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['memberId']" placeholder="请输入会员id"  ></a-input>
            </a-form-item>
          </a-col>
           <a-col :span="24" v-show="!flag">
            <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol" >
              <a-input v-decorator="['phone']" placeholder="请输入手机尾号" @blur="selectMember" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24" v-show="!flag" >
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol" >
              <a-input v-decorator="['name']" placeholder="请输入会员"  disabled></a-input>
            </a-form-item>
          </a-col>
         <!-- <a-col :span="24" v-show="flag">
            <a-form-item label="支付方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['consumtWay']" placeholder="请输入支付方式"  ></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :md="14" :sm="16">
           <a-form-item label="支付方式" v-show="flag">
                    <a-select  name="projectNameList" v-decorator="['consumtWay']" placeholder="请选择支付方式">
                        <a-select-option value="">请选择</a-select-option>
                        <a-select-option v-for="item in payWay" :key="item.name" :value="item.name"> {{item.name}}</a-select-option>
                    </a-select>
            </a-form-item>
          </a-col> 
          
             <a-col :md="14" :sm="16">
           <a-form-item label="项目名称">
                    <a-select  name="projectNameList" v-decorator="['consumptionItemsIdd']" placeholder="请选择消费项目">
                        <a-select-option value="">请选择</a-select-option>
                        <a-select-option v-for="item in itemsList" :key="item.id" :value="item.id"> {{item.content}}(原价/{{item.oriPrice}}元,会员价/{{item.memberPrice}})</a-select-option>
                    </a-select>
            </a-form-item>
           
          </a-col> 
         
          <a-col :span="24" v-show=false >
            <a-form-item label="员工id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['employeeId']" placeholder="请输入员工id"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="14" :sm="16">
           
            <a-form-item label="服务人员">
                    <a-select  name="projectNameList" v-decorator="['employeeId']" placeholder="请选择服务人员">
                        <a-select-option value="">请选择</a-select-option>
                        <a-select-option v-for="item in employeeList" :key="item.id" :value="item.id"> {{item.name}}</a-select-option>
                    </a-select>
            </a-form-item>
          </a-col>  
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'

  export default {
    name: 'ConsumptRecordForm',
    components: {
      JFormContainer,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        itemsList:[],
        payWay:[
          {name: "支付宝"},
          {name: "微信"},
          {name: "其他"}
        ],
        flag:false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/member/consumptRecord/add",
          edit: "/member/consumptRecord/edit",
          queryById: "/member/consumptRecord/queryById",
          selectMember:"/member/consumptRecord/selectMember",
          itemList:"/member/consumptionItems/itemList",
          employeeList:"/member/meMployee/queryEmployInfo"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
      this.itemList();
      this.employeeList();
    },
    methods: {
    //员工列表
    employeeList(){
        const that = this;
         console.info(111);
           httpAction(this.url.employeeList,{},'get').then((res)=>{
              console.info(res)
              if(res.success){
                  this.employeeList = res.result;
              }else{
                  console.warning(res.message);
              }
            })
    },
    //查询项目列表
    itemList(){
         httpAction(this.url.itemList,{},'get').then((res)=>{
              console.info(res)
              if(res.success){
                  this.itemsList = res.result;
                  console.info(this.itemList)
              }else{
                this.$message.warning(res.message);
              }
          })
    },
      //验证会员手机号
      selectMember(){
          let params = {'phone':this.form.getFieldValue('phone')};
          httpAction(this.url.selectMember+"?phone="+this.form.getFieldValue('phone'),{},'get').then((res)=>{
              console.info(res)
              if(res.success){
                  res.result.memberId = res.result.id;
                  this.form.setFieldsValue(pick(res.result,'memberId','name'))
                  this.$message.success(res.message);
              }else{
                  this.$message.warning(res.message+";请输入完整手机号");
              }
            })
      },
      add () {
        this.edit({});
      },
      edit (record) {
        console.info(record)
        if(record == 1){
          this.flag = true
        }else{
          this.flag = false
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'memberId','consumptionItemsIdd','consumtWay','employeeId','consumtAmount'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              console.info(res.message)
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                console.info(res.message)
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'memberId','consumptionItemsIdd','consumtWay','employeeId','consumtAmount'))
      },
    }
  }
</script>