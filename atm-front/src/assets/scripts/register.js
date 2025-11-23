import axios from "axios";
import NavBar from "@/components/NavBar.vue";

export default {
    components: { NavBar },

    data() {
        return {
            name: "",
            card: "",
            password: "",
            confirm: "",
            sex: "",
            msg: ""
        };
    },

    methods: {
        async submitRegister() {
            this.msg = "";

            if (!this.name || !this.card || !this.password || !this.confirm || !this.sex) {
                this.msg = "请填写所有字段";
                return;
            }

            if (this.password !== this.confirm) {
                this.msg = "两次密码不一致";
                return;
            }

            if (this.card.length < 8 || this.card.length > 20) {
                this.msg = "卡号长度必须在 8~20 位之间";
                return;
            }

            try {
                const res = await axios.post("http://localhost:8090/api/atm/register", {
                    name: this.name,
                    card: this.card,
                    password: this.password,
                    balance: 0,
                    limit: 20000,
                    sex: this.sex
                });

                this.msg = "开户成功！即将跳转登录...";
                setTimeout(() => this.$router.push("/login"), 1500);

            } catch (e) {
                this.msg = e.response?.data || "服务器错误";
            }
        }
    }
};
