<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="data.orderID" style="width: 260px;margin-right: 10px" placeholder="请输入订单编号查询"
                :prefix-icon="Search"/>
      <el-input v-model="data.productName" style="width: 260px;margin-right: 10px" placeholder="请输入产品名称查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-table :data="data.tableData" style="width: 100%">
          <el-table-column prop="planID" label="计划编号"></el-table-column>
          <el-table-column prop="orderID" label="订单编号"></el-table-column>
          <el-table-column prop="customerID" label="客户编号"></el-table-column>
          <el-table-column prop="productName" label="产品名称"></el-table-column>
          <el-table-column prop="productSum" label="产品数量"></el-table-column>
          <el-table-column prop="startTime" label="计划开始时间" width="150"></el-table-column>
          <el-table-column prop="endTime" label="计划结束时间" width="150"></el-table-column>
          <el-table-column prop="status" label="计划状态"></el-table-column>
          <el-table-column prop="totalAmount" label="总金额"></el-table-column>
          <el-table-column prop="remarks" label="备注信息"></el-table-column>
          <el-table-column prop="productionProcesses" label="生产流程列表" width="150"></el-table-column>
          <el-table-column prop="productionEquipments" label="生产设备列表" width="150"></el-table-column>
          <el-table-column prop="materials" label="所需物料列表" width="150"></el-table-column>

          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button v-if="scope.row.status === '未开始'" type="text" @click="dispatchWorkOrder(scope.row)">派发工单</el-button>
            </template>
          </el-table-column>

        </el-table>
      </div>
    </div>
    <div class="card">
      <el-pagination
          v-model="data.pageSize"
          v-model:currentPage="data.pageNum"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5, 10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="data.total">
      </el-pagination>
    </div>
    <!-- 弹窗 -->
    <el-dialog title="派发工单" v-model="dialogVisible" width="30%">
      <el-form :model="dispatchData" label-width="120px">

        <el-form-item label="工单编号">
          <el-input v-model="dispatchData.ticketID"></el-input>
        </el-form-item>

        <el-form-item label="计划编号">
          <el-input v-model="dispatchData.planID" readonly></el-input>
        </el-form-item>

        <el-form-item label="产品名称">
          <el-input v-model="dispatchData.productName" readonly></el-input>
        </el-form-item>

        <el-form-item label="车间">
          <el-select v-model="dispatchData.workshop" placeholder="请选择车间" @change="fetchWorkers">
            <el-option value="蜡模车间" label="蜡模车间"/>
            <el-option value="制壳车间" label="制壳车间"/>
            <el-option value="融化车间" label="融化车间"/>
            <el-option value="后处理车间" label="后处理车间"/>
            <el-option value="机加工车间" label="机加工车间"/>
            <el-option value="材料库" label="材料库"/>
            <el-option value="设备管理组" label="设备管理组"/>
            <el-option value="设备研发组" label="设备研发组"/>
            <el-option value="工装模具研发组" label="工装模具研发组"/>
            <el-option value="成品库" label="成品库"/>
            <el-option value="铸锻件外协采购和工序外协" label="铸锻件外协采购和工序外协"/>
          </el-select>
        </el-form-item>
        <el-form-item label="班组">
          <el-select v-model="dispatchData.team" placeholder="请选择班组" @change="fetchWorkers">
            <el-option value="模具库组" label="模具库组" v-if="dispatchData.workshop==='蜡模车间'"/>
            <el-option value="陶瓷芯班芯组" label="陶瓷芯班芯组" v-if="dispatchData.workshop==='蜡模车间'"/>
            <el-option value="射蜡组" label="射蜡组" v-if="dispatchData.workshop==='蜡模车间'"/>
            <el-option value="修蜡班" label="修蜡班" v-if="dispatchData.workshop==='蜡模车间'"/>
            <el-option value="组树班" label="组树班" v-if="dispatchData.workshop==='蜡模车间'"/>
            <el-option value="制壳班组" label="制壳班组" v-if="dispatchData.workshop==='制壳车间'"/>
            <el-option value="熔化班组班组" label="熔化班组班组" v-if="dispatchData.workshop==='融化车间'"/>
            <el-option value="切割班组班组" label="切割班组班组" v-if="dispatchData.workshop==='融化车间'"/>
            <el-option value="精磨班组" label="精磨班组" v-if="dispatchData.workshop==='融化车间'"/>
            <el-option value="抛丸班组" label="抛丸班组" v-if="dispatchData.workshop==='后处理车间'"/>
            <el-option value="调型班组" label="调型班组" v-if="dispatchData.workshop==='后处理车间'"/>
            <el-option value="手砂轮班组" label="手砂轮班组" v-if="dispatchData.workshop==='后处理车间'"/>
            <el-option value="工序外协组" label="工序外协组" v-if="dispatchData.workshop==='后处理车间'"/>
            <el-option value="数控班组" label="数控班组" v-if="dispatchData.workshop==='机加工车间'"/>
            <el-option value="钻床班组" label="钻床班组" v-if="dispatchData.workshop==='机加工车间'"/>
            <el-option value="清洗班组" label="清洗班组" v-if="dispatchData.workshop==='机加工车间'"/>
            <el-option value="材料库组" label="材料库组" v-if="dispatchData.workshop==='材料库'"/>
            <el-option value="设备管理组" label="设备管理组" v-if="dispatchData.workshop==='设备管理组'"/>
            <el-option value="设备研发组" label="设备研发组" v-if="dispatchData.workshop==='设备研发组'"/>
            <el-option value="工装摸具组" label="工装摸具组" v-if="dispatchData.workshop==='工装模具研发组'"/>
            <el-option value="成品库组" label="成品库组" v-if="dispatchData.workshop==='成品库'"/>
            <el-option value="铸锻件外协采购和工序外协组" label="铸锻件外协采购和工序外协组"
                       v-if="dispatchData.workshop==='铸锻件外协采购和工序外协'"/>
          </el-select>
        </el-form-item>
        <el-form-item label="工人">
          <el-select v-model="dispatchData.worker" placeholder="请选择工人" @change="handleWorkerChange">
            <el-option
                v-for="worker in dispatchData.workers"
                :key="worker.name"
                :label="worker.name"
                :value="worker.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工人历史">
          <el-input v-model="dispatchData.selectedWorkerHistory" readonly></el-input>
        </el-form-item>

        <el-form-item label="工人工级">
          <el-input v-model="dispatchData.grade" readonly></el-input>
        </el-form-item>


        <el-form-item label="所需完成的工序">
          <el-select v-model="dispatchData.process" multiple placeholder="请选择所需工序">

            <el-option label="射蜡件" value="射蜡件"></el-option>
            <el-option label="射水溶芯" value="射水溶芯"></el-option>
            <el-option label="射尿素芯" value="射尿素芯"></el-option>
            <el-option label="化芯" value="化芯"></el-option>
            <el-option label="射刺头" value="射刺头"></el-option>
            <el-option label="射冷蜡块" value="射冷蜡块"></el-option>
            <el-option label="射牙套" value="射牙套"></el-option>
            <el-option label="射帽" value="射帽"></el-option>

          </el-select>
        </el-form-item>

        <el-form-item label="工作开始日期" prop="startDate">
          <el-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="dispatchData.startDate"></el-date-picker>
        </el-form-item>

        <el-form-item label="工作结束日期" prop="startDate">
          <el-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="dispatchData.endDate"></el-date-picker>
        </el-form-item>


        <el-form-item label="所需完成的数量">
          <el-input v-model="dispatchData.number" ></el-input>
        </el-form-item>


      </el-form>

      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="assignTask">确认分配</el-button>
      </span>

    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, watch } from 'vue';
import { Search } from '@element-plus/icons-vue';
import request from '@/utils/request';
import {ElMessage} from "element-plus";

export default {
  setup() {
    const data = reactive({
      orderID: '',
      productName: '',
      tableData: [],
      total: 0,
      pageSize: 5,
      pageNum: 1,
    });

    const dialogVisible = ref(false);
    const dispatchData = reactive({
      planID: '',
      process:'',
      ticketID:'',
      productName: '',
      num:'',
      workshop: '',
      team: '',
      worker: '',
      workers: [], // 用于存储工人列表
      selectedWorkerHistory: '', // 用于存储选中工人的历史信息
      grade:'',
    });

    const fetchWorkers = () => {
      const params = {
        workshop: dispatchData.workshop,
        teamsGroups: dispatchData.team,
      };
      request.get('/selectname', {params})
          .then((res) => {
            if (Array.isArray(res)) {

              dispatchData.workers = res.map(worker => ({
                name: worker.name, // 只保留姓名作为label
                history: worker.history, // 保留历史信息
                grade:worker.grade,
              }));
            } else {
              console.error('Invalid response format:', res);
              dispatchData.workers = [];
            }
          })
          .catch((error) => {
            console.error('Error fetching workers: ', error);
            dispatchData.workers = [];
          });
    };

    const handleWorkerChange = (selectedWorkerName) => {
      // 当选择一个工人时，查找该工人的历史并填充
      const selectedWorker = dispatchData.workers.find(worker => worker.name === selectedWorkerName);
      if (selectedWorker) {
        dispatchData.selectedWorkerHistory = selectedWorker.history;
        dispatchData.grade=selectedWorker.grade;
      } else {
        dispatchData.selectedWorkerHistory = '';
      }
    };

    // 监听车间和班组的变化
    watch(() => [dispatchData.workshop, dispatchData.team], ([newWorkshop, newTeam]) => {
      if (newWorkshop && newTeam) {
        fetchWorkers();
      }
    });

    // 初始化页面加载
    const load = () => {
      request.get('/productionplan/selectPage', {
        params: {
          pageNum: data.pageNum,
          pageSize: data.pageSize,
          orderID: data.orderID,
          productName: data.productName,
        },
      })
          .then((res) => {
            data.tableData = res.data?.list || [];
            data.total = res.data?.total || 0;
          })
          .catch((error) => {
            console.error('Error fetching data: ', error);
            // 这里可以添加错误处理逻辑，比如显示错误消息
          });
    };
    load();

    // 翻页事件
    const handleCurrentChange = (newPage) => {
      data.pageNum = newPage;
      load();
    };
    const handleSizeChange = (newSize) => {
      data.pageSize = newSize;
      load();
    };

    const reset = () => {
      data.orderID = '';
      data.productName = '';
      data.pageNum = 1; // 重置页码到第一页
      data.pageSize = 5; // 重置页面大小到默认值
      load();
    };

    // 派发工单
    const dispatchWorkOrder = (row) => {
      dispatchData.planID = row.planID;
      dispatchData.productName = row.productName;
      dispatchData.workshop = ''; // 重置车间选择
      dispatchData.team = ''; // 重置班组选择
      dispatchData.worker = ''; // 重置工人选择
      dispatchData.workers = []; // 清空工人列表
      dispatchData.selectedWorkerHistory = ''; // 清空选中工人的历史信息
      dialogVisible.value = true;
    };

// 分配任务
    // 分配任务
    const assignTask = () => {
      if (!dispatchData.ticketID || !dispatchData.planID || !dispatchData.productName || !dispatchData.process || !dispatchData.startDate || !dispatchData.endDate || !dispatchData.number) {
        ElMessage.error("请填写所有必填项");
        return;
      }

      let process = dispatchData.process.join(',');
      dispatchData.process=process;


      // 发送请求到服务器
      request.post('/ticket/add', {
        ticketID: dispatchData.ticketID,
        planID: dispatchData.planID,
        productName: dispatchData.productName,
        process: dispatchData.process,
        startDate: dispatchData.startDate,
        endDate: dispatchData.endDate,
        number: dispatchData.number,
        workshop: dispatchData.workshop,
        team: dispatchData.team,
        worker: dispatchData.worker,
        grade: dispatchData.grade
      })
          .then(response => {
            if (response.code==200) {
              ElMessage.success("工单分配成功");
              dialogVisible.value = false;
              load(); // 重新加载数据
            } else {
              ElMessage.error("工单分配失败: " + response.message);
            }
          })
          .catch(error => {
            console.error('Error assigning task: ', error);
            ElMessage.error("工单分配失败");
          });
    };

// 返回所有需要在模板中使用的响应式数据和方法
    return {
      data,
      dialogVisible,
      dispatchData,
      load,
      handleCurrentChange,
      handleSizeChange,
      reset,
      dispatchWorkOrder,
      assignTask,
      Search,
      fetchWorkers,
      handleWorkerChange,
    };
  }
}

</script>

<style scoped> .card { padding: 10px; border: 1px solid #ddd; border-radius: 4px; } .dialog-footer { text-align: right; } </style>






