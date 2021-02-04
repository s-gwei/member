<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
            <a-col :md="6" :sm="8">
            <a-form-item label="员工姓名">
              <!--<a-input placeholder="请输入账号查询" v-model="queryParam.username"></a-input>-->
              <!-- <j-input placeholder="请选择客户" v-model="queryParam.username"></j-input>
               -->
                <a-select  name="projectNameList" v-model="queryParam.employeeId" placeholder="请选择员工" >
                        <a-select-option value="">请选择</a-select-option>
                        <a-select-option v-for="item in meMList" :key="item.id" :value="item.id" >{{item.name}}</a-select-option>
                    </a-select>
            </a-form-item>
          </a-col>
           <a-col :md="12" :sm="12">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-range-picker
                style="width: 210px"
                v-model="queryParam.createTimeRange"
                format="YYYY-MM-DD"
                :placeholder="['开始时间', '结束时间']"
                @change="onDateChange"
                @ok="onDateOk"
              />
            </a-form-item>
         
          </a-col>
             <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
             
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">会员消费</a-button>
      <a-button @click="handleEdit(1)" type="primary" icon="plus">非会员消费</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('消费记录表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <!-- <a-button type="primary" icon="import">导入</a-button> -->
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <!-- <a @click="handleEdit(record)">编辑</a> -->

          <!-- <a-divider type="vertical" /> -->
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <consumpt-record-modal ref="modalForm" @ok="modalFormOk"></consumpt-record-modal>
  </a-card>
</template>

<script>

  import { filterObj } from '@/utils/util'
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ConsumptRecordModal from './modules/ConsumptRecordModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'
  import { httpAction, getAction } from '@/api/manage'

  export default {
    name: 'ConsumptRecordList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ConsumptRecordModal,
      JSuperQuery,
    },
    data () {
      return {
        
        meMList:[],
        description: '消费记录表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'会员',
            align:"center",
            dataIndex: 'memberName'
          },
          {
            title:'消费项目',
            align:"center",
            dataIndex: 'consumpName'
          },{
            title:'消费金额',
            align:"center",
            dataIndex: 'consumtAmount'
          },
          {
            title:'支付方式',
            align:"center",
            dataIndex: 'payWay'
          },
          {
            title:'员工',
            align:"center",
            dataIndex: 'employeeName'
          },
          {
            title:'消费时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/member/consumptRecord/list",
          delete: "/member/consumptRecord/delete",
          deleteBatch: "/member/consumptRecord/deleteBatch",
          exportXlsUrl: "/member/consumptRecord/exportXls",
          importExcelUrl: "member/consumptRecord/importExcel",
          queryEmployInfo:"member/meMployee/queryEmployInfo",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    this.queryEmployInfo();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
        //查询员工信息
      queryEmployInfo(){
            getAction(this.url.queryEmployInfo).then((res)=>{
           console.info(res);
           this.meMList = res.result;
           console.info(this.meMList);
          });

      },
            getQueryParams(){
            console.info(this.queryParam);
        var param = Object.assign({}, this.queryParam,this.isorter);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        delete param.createTimeRange; // 时间参数不传递后台
        if (this.superQueryParams) {
          param['superQueryParams'] = encodeURI(this.superQueryParams)
          param['superQueryMatchType'] = this.superQueryMatchType
        }
        return filterObj(param);
      },
     //查询时间段
      onDateChange: function (value, dateString) {
        console.log(dateString[0],dateString[1]);
        this.queryParam.createTime_begin=dateString[0];
        this.queryParam.createTime_end=dateString[1];
      },
      onDateOk(value) {
        console.log(value);
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'memberId',text:'会员id',dictCode:''})
        fieldList.push({type:'string',value:'consumptionItemsIdd',text:'消费项目',dictCode:''})
        fieldList.push({type:'string',value:'consumtWay',text:'支付方式',dictCode:''})
        fieldList.push({type:'string',value:'employeeId',text:'员工id',dictCode:''})
        fieldList.push({type:'double',value:'consumtAmount',text:'消费金额',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>