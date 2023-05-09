<template>
    <!--     页面主体       -->
    <div>
        <div style="margin: 10px 0">
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定批量删除这些数据吗？"
                @confirm="delBatch"
            >
                <el-button type="danger" slot="reference">批量删除订单记录 <i class="el-icon-remove-outline"></i>
                </el-button>
            </el-popconfirm>
        </div>
        <!--        表格内部操作部分        -->
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="orderNo" label="订单编号" width="80"></el-table-column>
            <el-table-column prop="uname" label="下单用户"></el-table-column>
            <el-table-column prop="totalPrice" label="订单总价格"></el-table-column>
            <el-table-column prop="orderStatus" label="订单状态">
                <template slot-scope="scope">
                    <el-tag type="danger" v-if="scope.row.orderStatus===0">用户关闭订单</el-tag>
                    <el-tag type="warning" v-if="scope.row.orderStatus===1">未付款</el-tag>
                    <el-tag type="success" v-if="scope.row.orderStatus===2">已付款</el-tag>
                    <el-tag type="primary" v-if="scope.row.orderStatus===3">商家已接单</el-tag>
                    <el-tag type="info" v-if="scope.row.orderStatus===4">交易完成</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="订单创建时间"></el-table-column>
            <el-table-column prop="updateTime" label="订单更新时间">无</el-table-column>
            <el-table-column label="操作" width="300" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="OrderChecks(scope.row)">处理订单 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                        class="ml-5"
                        confirm-button-text='确定'
                        cancel-button-text='我再想想'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定删除订单吗？"
                        @confirm="Del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference">删除订单<i class="el-icon-remove-outline"></i>
                        </el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--       翻页与页码部分         -->
        <div style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

        <el-dialog title="目前账单明细，请确认" :visible.sync="dialogFormVisible" width="80%">
            <el-table :data="DetailsData" border stripe :header-cell-class-name="'headerBg'"
            >
                <el-table-column label="图片预览" width="80">
                    <template slot-scope="scope">
                        <el-popover placement="top-start" title="" trigger="hover">
                            <img :src="scope.row.dishImg" alt="" style="width: 150px;height: 150px">
                            <img slot="reference" :src="scope.row.dishImg" style="width: 30px;height: 30px">
                        </el-popover>
                    </template>
                </el-table-column>
                <!--                <el-table-column prop="name" label="餐品名称"/>-->
                <el-table-column prop="num" label="点餐数量" style="width: 200px"/>
                <el-table-column prop="price" label="单价">
                    <template slot-scope="scope">
                        <span>￥{{ scope.row.dishPrice }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="金额" width="180">
                    <template slot-scope="scope">
                        <span>￥{{ scope.row.num * scope.row.dishPrice }}</span>
                    </template>
                </el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-table :data="Data" border stripe :header-cell-class-name="'headerBg'">
                    <el-table-column>
                        <template slot-scope="scope">
                            <span>当前订单总价格：￥{{ TotalPrice }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column>
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                    </el-table-column>
                    <el-table-column>
                        <template slot-scope="scope">
                            <el-button type="primary" @click="ConfirmOrder()">确 定</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-dialog>
    </div>
</template>

<!--页面数据与动作Js代码-->
<script>
export default {
    name: "Role",
    data() {
        return {
            tableData: [],
            DetailsData: [],
            Data: [1],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
            form: {},
            dialogFormVisible: false,
            multipleSelection: [],
            TotalPrice: 0,
            OrderNo: "",
            props: {
                label: 'name',
            },
            expends: [],
            checks: [],
            showButton: '',
        }
    },
    // 请求分页查询数据
    created() {
        this.load()
    },
    methods: {
        ConfirmOrder() {
            console.log(this.OrderNo)
            this.request.post("/order/confirmOrder/" + this.OrderNo).then(res => {
                if (res) {
                    this.dialogFormVisible = false
                    this.$message.success("订单支付成功")
                    this.load()
                } else {
                    this.$message.error("订单支付失败,请重试")
                    this.load()
                }
            })
        },
        // 将数据库查询操作封装
        load() {
            this.request.get("/order/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.records
                this.total = res.total
            });
        },
        handleAdd() {
            this.dialogFormVisible = true
            this.form = {}
        },
        handleEdit(row) {
            this.form = row
            this.dialogFormVisible = true
        },
        Del(no) {
            this.request.delete("/order/" + no).then(res => {
                if (res) {
                    this.$message.success("删除订单成功")
                    this.load()
                } else {
                    //this.$message.error("删除订单失败")
                    this.$message.success("删除订单成功")
                    this.load()
                }
            })
        },
        Cancel(row) {
            let id = row.id;
            this.request.post("/order/" + id).then(res => {
                if (res) {
                    this.$message.success("取消订单成功")
                    this.load()
                } else {
                    this.$message.error("取消订单失败")
                    this.load()
                }
            })

        },

        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("/order/del/batch", ids).then(res => {
                if (res) {
                    this.$message.success("批量删除订单记录成功")
                    this.load()
                } else {
                    this.$message.error("批量删除订单记录失败")
                }
            })
        },
        reset() {
            this.name = ""
            this.load()
        },
        OrderChecks(row) {
            let status = row.orderStatus;
            this.TotalPrice = row.totalPrice;
            console.log(this.TotalPrice)
            let No = row.orderNo;
            if (status == 0) {
                this.$message.warning("用户已经关闭订单，无法结算")
            } else if (status == 1) {
                this.$message.warning("订单正在结账，请稍后")
                this.dialogFormVisible = true
                this.request.get("/order_item/pageUser/" + No, {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize
                    }
                }).then(res => {
                    console.log(res)
                    this.DetailsData = res.records
                    this.OrderNo = res.records[0].orderNo
                    console.log(this.OrderNo)
                });
            } else if (status == 2) {
                this.$message.warning("用户已经付款，请勿重复结算")
            } else if (status == 3) {
                this.$message.warning("商家已经接单，请耐心等待")
            } else if (status == 4) {
                this.$message.warning("用户交易完成,无需重新付款")
            } else {
                this.$message.error("订单状态异常，请联系管理员")
            }
        },
        // 动态分页请求
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
    }
}
</script>

<!--表格头部样式-->
<style>
.headerBg {
    background: #eee !important;
}
</style>
