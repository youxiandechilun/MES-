
<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input v-model="searchParams.contractNumber" style="width: 260px;margin-right: 10px" placeholder="请输入合同编号查询"
                :prefix-icon="Search"/>
      <el-input v-model="searchParams.productName" style="width: 260px;margin-right: 10px" placeholder="请输入产品名称查询"
                :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:10px" @click="loadOrders">查询</el-button>
      <el-button type="info" @click="resetSearch">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 10px">
      <el-table :data="orders" style="width: 100%">
        <el-table-column prop="orderID" label="订单编号"></el-table-column>
        <el-table-column prop="customerID" label="客户编号"></el-table-column>
        <el-table-column prop="contractNumber" label="合同编号"></el-table-column>
        <el-table-column prop="productName" label="产品名称"></el-table-column>
        <el-table-column prop="orderType" label="订单类型"></el-table-column>
        <el-table-column
            prop="generateProductionPlan"
            label="是否生成生产计划"
            width="180">
          <template #default="{ row }">
            <el-button
                type="success"
                plain
                :disabled="row.generateProductionPlan !== '0'"
                @click="generateProductionPlan(row)">
              {{ row.generateProductionPlan === '0' ? '生成生产计划' : '已生成生产计划' }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="productSum" label="产品数量"></el-table-column>
        <el-table-column prop="startTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="status" label="订单状态"></el-table-column>
        <el-table-column prop="totalAmount" label="总金额"></el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:pageSize="pageSize"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :page-sizes="[5,10, 20, 50, 100]"
          :layout="'sizes, prev, pager, next'"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="生成生产计划" v-model="productionPlan.dialogVisible" width="50%" @close="resetForm">
      <el-form :model="productionPlan">

        <el-form-item label="计划编号" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.planID" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item label="订单编号" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.orderID" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="客户编号" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.customerID" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="产品名称" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.productName" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="产品数量" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.productSum" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="计划开始时间" :label-width="formLabelWidth">
          <el-date-picker v-model="productionPlan.startTime" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>

        <el-form-item label="计划结束时间" :label-width="formLabelWidth">
          <el-date-picker v-model="productionPlan.endTime" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>

        <el-form-item label="计划状态" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.status" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="总金额" :label-width="formLabelWidth">
          <el-input v-model="productionPlan.totalAmount" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="生产流程" :label-width="formLabelWidth">
          <el-select v-model="productionPlan.productionProcesses" placeholder="请选择生产流程" autocomplete="off">
            <el-option
                label="射蜡件->射水溶芯->射尿素芯->化芯->射刺头->射冷蜡块->射牙套->射帽"
                value="射蜡件->射水溶芯->射尿素芯->化芯->射刺头->射冷蜡块->射牙套->射帽"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="生产设备" :label-width="formLabelWidth">
          <el-select v-model="productionPlan.productionEquipments" multiple placeholder="请选择生产设备">
            <el-option label="射蜡机" value="射蜡机"></el-option>
            <el-option label="电热板" value="电热板"></el-option>
            <el-option label="冰水机" value="冰水机"></el-option>
            <el-option label="催化燃烧" value="催化燃烧"></el-option>
            <el-option label="中央空调" value="中央空调"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="所需物料" :label-width="formLabelWidth">
          <el-select v-model="productionPlan.materials" multiple placeholder="请选择所需物料">
            <!-- 主料分组 -->
            <el-option disabled value="主料">主料</el-option>
            <el-option label="162蜡" value="162蜡"></el-option>
            <el-option label="168蜡" value="168蜡"></el-option>
            <el-option label="自制蜡" value="自制蜡"></el-option>
            <el-option label="水溶蜡" value="水溶蜡"></el-option>
            <el-option label="尿素" value="尿素"></el-option>
            <el-option label="脱模剂" value="脱模剂"></el-option>
            <el-option label="石英芯" value="石英芯"></el-option>

            <!-- 辅料分组 -->
            <el-option disabled value="辅料">辅料</el-option>
            <el-option label="油封" value="油封"></el-option>
            <el-option label="密封圈" value="密封圈"></el-option>
            <el-option label="尘封" value="尘封"></el-option>
            <el-option label="气管" value="气管"></el-option>
            <el-option label="气枪" value="气枪"></el-option>
          </el-select>
        </el-form-item>



        <el-form-item label="备注信息" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="productionPlan.remarks" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="productionPlan.dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitProductionPlan">确 定</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { Search } from "@element-plus/icons-vue";
import request from "@/utils/request";
import { ElMessage } from "element-plus";

const productionPlan = reactive({
  contractNumber:'',
  planID: '',
  orderID: '',
  customerID: '',
  productName: '',
  productSum: '',
  startTime: '',
  endTime: '',
  status: '',
  totalAmount: '',
  remarks: '',
  productionProcesses: '',
  productionEquipments: '',
  materials: '',
  workshop: '',
  dialogVisible: false,
});

const formLabelWidth = ref('120px');

const generateProductionPlan = (row) => {

  productionPlan.contractNumber=row.contractNumber;
  productionPlan.status = '未开始';
  productionPlan.orderID = row.orderID;
  productionPlan.customerID=row.customerID;
  productionPlan.productName = row.productName;
  productionPlan.productSum = row.productSum;
  productionPlan.totalAmount=row.totalAmount;
  productionPlan.dialogVisible = true;

};

const submitProductionPlan = () => {
  if (!productionPlan.orderID || !productionPlan.startTime || !productionPlan.endTime || !productionPlan.status || !productionPlan.productName || !productionPlan.productSum) {
    ElMessage.error("请填写所有必填项");
    return;
  }

  let productionEquipmentsString = productionPlan.productionEquipments.join(',');
  let productionMaterialsString = productionPlan.materials.join(',');

  productionPlan.productionEquipments=productionEquipmentsString;
  productionPlan.materials=productionMaterialsString;


  request.post("/productionplan/add", productionPlan).then((res) => {
    if (res.code === '200') {
      ElMessage.success("生产任务生成成功！");
      // 更新订单状态和生产计划生成状态
      const updateData = {
        contractNumber: productionPlan.contractNumber,
        status: '开始中',
        generateProductionPlan: '1'
      };
      request.put("/order/update2", updateData).then((res) => {
        if (res.code === '200') {
          productionPlan.dialogVisible = false;
          loadOrders();
        } else {
          ElMessage.error("更新失败：" + res.msg);
        }
      });
    } else {
      ElMessage.error(res.msg);
    }
  });
};

const searchParams = reactive({
  contractNumber: '',
  productName: '',
});

const orders = ref([]);
const total = ref(0);
const pageSize = ref(5);
const currentPage = ref(1);

// 加载订单数据
const loadOrders = () => {
  request.get("/order/selectPage", {
    params: {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      contractNumber: searchParams.contractNumber,
      productName: searchParams.productName,
    }
  }).then(res => {
    orders.value = res.data?.list || [];
    total.value = res.data?.total || 0;
  });
};
loadOrders();

// 翻页事件
const handleCurrentChange = () => {
  loadOrders();
};
const handleSizeChange = () => {
  loadOrders();
};

// 重置搜索
const resetSearch = () => {
  searchParams.contractNumber = '';
  searchParams.productName= '';
  loadOrders();
};

// 重置生产计划表单
const resetForm = () => {
  productionPlan.planID = '';
  productionPlan.orderID = '';
  productionPlan.customerID = '';
  productionPlan.productName = '';
  productionPlan.productSum = '';
  productionPlan.startTime = '';
  productionPlan.endTime = '';
  productionPlan.status = '';
  productionPlan.totalAmount = '';
  productionPlan.remarks = '';
  productionPlan.productionProcesses = '';
  productionPlan.productionEquipments = '';
  productionPlan.materials = '';
  productionPlan.workshop = '';
};
</script>

<style scoped> .card { padding: 10px; border: 1px solid #ddd; border-radius: 4px; } .dialog-footer { text-align: right; } </style>


