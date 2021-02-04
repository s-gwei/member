<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当日进账" total="" >
          <h1>{{saleInfo.todayIncome}}元</h1>
        </chart-card>
      </a-col>
            <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当日冲卡额" total="" >
          <h1 >{{saleInfo.salesToday}}元</h1>
        </chart-card>
      </a-col>
               <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当日消费人数" total="" >
          <h1>{{saleInfo.todayNumber}}人次</h1>
        </chart-card>
      </a-col>
               <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当月进账" total="" >
          <h1>{{saleInfo.monthIncome}}元</h1>
        </chart-card>
      </a-col>
    </a-row>
    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
    
          <a-tab-pane loading="true" tab="每月进账" key="1">
            <a-row>
              <a-col :xl="24" :lg="24" :md="12" :sm="24" :xs="24">
                <bar title="销售额排行" :dataSource="barData"/>
              </a-col>
             
            </a-row>
          </a-tab-pane>
        
        </a-tabs>
      </div>
    </a-card>
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import { httpAction, getAction } from '@/api/manage'
  import Trend from '@/components/Trend'
  import { getLoginfo,getVisitInfo } from '@/api/api'

  const rankList = []
  for (let i = 0; i < 7; i++) {
    rankList.push({
      name: '白鹭岛 ' + (i+1) + ' 号店',
      total: 1234.56 - i * 100
    })
  }
  const barData = []

  export default {
    name: "IndexChart",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        url: {
          querySaleInfo: "/member/meRechargeRecord/recordInfo",
          queryCur12Total:"/member/meRechargeRecord/queryCur12Total"
        },
       
        saleInfo:{
           todayIncome:'',
           salesToday:'',
           todayNumber:'',
           monthIncome:''

        },
        loading: true,
        center: null,
        rankList,
        barData,
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        indicator: <a-icon type="loading" style="font-size: 24px" spin />
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initLogInfo();
      this.querySaleInfo();
      this.queryCur12Total();

    },
    methods: {
      //查询最近12个月数据
      queryCur12Total(){
         getAction(this.url.queryCur12Total).then((res)=>{
           console.info(res);
           let list = res.result;
        for (let i = 0; i < list.length; i += 1) {
         barData.push({
         x: `${list[i].month}月`,
         y: list[i].total
    })
  }
          //  this.saleInfo = res.result;
          });
      },
      //查询销售数据
      querySaleInfo(){
          getAction(this.url.querySaleInfo).then((res)=>{
           console.info(res.result);
           if(res.result== null){
            this.saleInfo.todayIncome=0,
           this.saleInfo.salesToday=0,
           this.saleInfo.todayNumber=0,
           this.saleInfo.monthIncome=0
           } else{
             this.saleInfo = res.result;
           }
          });
      },
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
             this.visitInfo = res.result;
           }
         })
      },
    }
  }
</script>

<style lang="less" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>