<template>
    <div class="main">
        <div class="top">
            <div class="header">
                <div>
                    <img alt="logo" class="logo" src="@/assets/logo.svg">
                    <span class="title">Awesome Hotel</span>
                </div>
            </div>
            <div class="desc">

            </div>
        </div>
        <a-form
                :form="form"
                class="user-layout-login"
                id="formLogin"
                ref="formLogin"
        >
            <a-tabs
                    :activeKey="customActiveKey"
                    :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
                    @change="handleTabClick"
            >
                <a-tab-pane key="login" tab="账号密码登录">
                    <!-- 登录邮箱 -->
                    <a-form-item>
                        <a-input
                                placeholder="邮箱"
                                size="large"
                                type="text"
                                v-decorator="[
                'username',
                {rules: [{ required: true, message: '请输入邮箱地址' }], validateTrigger: 'blur'}
              ]"
                        >
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="user"/>
                        </a-input>
                    </a-form-item>
                    <!-- 登录密码 -->
                    <a-form-item>
                        <a-input
                                autocomplete="false"
                                placeholder="密码"
                                size="large"
                                type="password"
                                v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
                        >
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="lock"/>
                        </a-input>
                    </a-form-item>
                    <a-form-item>
                        <a-checkbox style="float: right" v-model="checked">
                            记住我
                        </a-checkbox>
                    </a-form-item>
                    <!-- 登陆确认按钮 -->
                    <a-form-item style="margin-top:24px">
                        <a-button
                                :loading="loginLoading"
                                @click="handlelogin()"
                                class="login-button"
                                size="large"
                                type="primary"
                        >确定
                        </a-button>
                    </a-form-item>
                </a-tab-pane>

                <a-tab-pane key="register" tab="注册新账号">
                    <!-- 邮箱  -->
                    <a-form-item>
                        <a-input
                                placeholder="邮箱"
                                size="large"
                                type="email"
                                v-decorator="[
              'registerUserMail',
              {rules: [{ required: true, type: 'email', message: '请输入邮箱' }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="mail"/>
                        </a-input>
                    </a-form-item>
                    <!-- 用户名  -->
                    <a-form-item>
                        <a-input
                                placeholder="用户名"
                                size="large"
                                v-decorator="[
              'registerUsername',
              {rules: [{ required: true, message: '请输入用户名' }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="user"/>
                        </a-input>
                    </a-form-item>
                    <!-- 手机号  -->
                    <a-form-item>
                        <a-input
                                placeholder="手机号"
                                size="large"
                                v-decorator="[
              'registerPhoneNumber',
              {rules: [{ required: true, message: '请输入手机号' }, {validator: this.handlePhoneNumber }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="book"/>
                        </a-input>
                    </a-form-item>
                    <!-- 密码  -->
                    <a-form-item>
                        <a-input
                                placeholder="密码"
                                size="large"
                                type="password"
                                v-decorator="[
                'registerPassword',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="lock"/>
                        </a-input>
                    </a-form-item>
                    <!-- 确认密码 -->
                    <a-form-item>
                        <a-input
                                placeholder="确认密码"
                                size="large"
                                type="password"
                                v-decorator="[
                'registerPasswordconfirm',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur'}]">
                            <a-icon :style="{ color: 'rgba(0,0,0,.25)' }" slot="prefix" type="lock"/>
                        </a-input>
                    </a-form-item>
                    <!-- 确认按钮 -->
                    <a-form-item style="margin-top:24px">
                        <a-button
                                :loading="registerLoading"
                                @click="handleRegister()"
                                class="login-button"
                                size="large"
                                type="primary"
                        >确定
                        </a-button>
                    </a-form-item>
                </a-tab-pane>
            </a-tabs>
        </a-form>

    </div>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import {message} from 'ant-design-vue';

    export default {
        name: 'login',
        components: {},
        data() {
            return {
                customActiveKey: 'login',
                loginLoading: false,
                registerLoading: false,
                form: this.$form.createForm(this),
                checked: true,
            }
        },
        computed: {
            ...mapGetters([
                'token'
            ])
        },
        mounted() {
            this.getCookie()
        },
        watch: {
            $route: {
                handler: function (route) {
                    this.redirect = route.query && route.query.redirect
                },
                immediate: true
            },
        },
        methods: {
            ...mapActions([
                'login',
                'register'
            ]),

            // handler
            handleUsernameOrEmail(rule, value, callback) {
                const {state} = this
                const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
                if (regex.test(value)) {
                    callback()
                } else {
                    callback(new Error('请输入有效用户名或邮箱'))
                }
                callback()
            },

            handleEmail(rule, value, callback) {
                const re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
                if (re.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入有效邮箱'));
                }
                callback()
            },

            handlePhoneNumber(rule, value, callback) {
                const re = /1\d{10}/;
                if (re.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入有效手机号'));
                }
                callback()
            },

            handlePassword(rule, value, callback) {
                if (value.length < 6) {
                    callback(new Error('密码长度至少6位'))
                }
                callback()
            },

            handlePasswordCheck(rule, value, callback) {
                const password = this.form.getFieldValue('registerPassword')
                if (value === undefined) {
                    callback(new Error('请输入密码'))
                }
                if (value && password && value.trim() !== password.trim()) {
                    callback(new Error('两次密码不一致'))
                }
                callback()
            },

            handleTabClick(key) {
                this.customActiveKey = key
            },

            handlelogin() {
                const validateFieldsKey = this.customActiveKey === 'login' ? ['username', 'password'] : ['registerUsername', 'registerUserMail', 'registerPassword', 'registerPasswordconfirm']
                this.form.validateFields(validateFieldsKey, {force: true}, async (err, values) => {
                    if (!err) {
                        this.loginLoading = true
                        const data = {
                            email: this.form.getFieldValue("username"),
                            password: this.form.getFieldValue("password")
                        }
                        if (this.checked){
                            this.setCookie(data.email, data.password,7)
                        }else {
                            this.clearCookie();
                        }
                        await this.login(data)
                        this.loginLoading = false
                    }
                })
            },

            handleRegister() {
                const {form: {validateFields}} = this;
                const validateFieldsKey = this.customActiveKey === 'login' ? ['username', 'password'] : ['registerUsername', 'registerPhoneNumber', 'registerUserMail', 'registerPassword', 'registerPasswordconfirm']
                validateFields(validateFieldsKey, {force: true}, async (err, values) => {
                    if (!err) {
                        this.registerLoading = true;
                        const data = {
                            email: this.form.getFieldValue('registerUserMail'),
                            password: this.form.getFieldValue('registerPassword'),
                            phoneNumber: this.form.getFieldValue('registerPhoneNumber'),
                            userName: this.form.getFieldValue('registerUsername'),
                            credit: 100,
                            userType: 0
                        }
                        await this.register(data).then(() => {
                            this.customActiveKey = 'login';
                            this.form.setFieldsValue({
                                'username': data.email,
                                'password': data.password,
                                'registerUserMail': '',
                                'registerUsername': '',
                                'registerPhoneNumber': '',
                                'registerPassword': '',
                                'registerPasswordconfirm': ''
                            })
                        });
                        this.registerLoading = false
                    } else {
                        message.error("请输入正确的信息")
                    }
                });
            },

            setCookie(name, pwd, days) {
                var exdate = new Date(); //获取时间
                exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * days); //保存的天数
                //字符串拼接cookie
                window.document.cookie = "username" + "=" + name + ";path=/;expires=" + exdate.toGMTString();
                window.document.cookie = "password" + "=" + pwd + ";path=/;expires=" + exdate.toGMTString();
            },

            clearCookie: function() {
                this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
            },

            getCookie() {
                if (document.cookie.length > 0) {
                    const arr = document.cookie.split("; "); //这里显示的格式需要切割一下自己可输出看下
                    for (let i = 0; i < arr.length; i++) {
                        const pool = arr[i].split("="); //再次切割
                        if (pool[0] === "username") {
                            this.form.setFieldsValue({
                                'username': pool[1]
                            }); //保存到保存数据的地方
                        } else if (pool[0] === "password") {
                            this.form.setFieldsValue({
                                'password': pool[1]
                            })
                        }
                    }
                }
            },
        }
    }
</script>

<style lang="less" scoped>

    .main {
        min-width: 260px;
        width: 368px;
        height: 1500px;
        margin: 100px auto;

        .top {
            text-align: center;

            .header {
                height: 44px;
                line-height: 44px;

                .badge {
                    position: absolute;
                    display: inline-block;
                    line-height: 1;
                    vertical-align: middle;
                    margin-left: -12px;
                    margin-top: -10px;
                    opacity: 0.8;
                }

                .logo {
                    height: 44px;
                    vertical-align: top;
                    margin-right: 16px;
                    border-style: none;
                }

                .title {
                    font-size: 33px;
                    color: rgba(0, 0, 0, .85);
                    font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                    font-weight: 600;
                    position: relative;
                    top: 2px;
                }
            }

            .desc {
                font-size: 14px;
                color: rgba(0, 0, 0, 0.45);
                margin-top: 12px;
                margin-bottom: 40px;
            }
        }
    }

    .user-layout-login {
        label {
            font-size: 14px;
        }

        .getCaptcha {
            display: block;
            width: 100%;
            height: 40px;
        }

        .forge-password {
            font-size: 14px;
        }

        button.login-button {
            padding: 0 15px;
            font-size: 16px;
            height: 40px;
            width: 100%;
        }

        .user-login-other {
            text-align: left;
            margin-top: 24px;
            line-height: 22px;

            .item-icon {
                font-size: 24px;
                color: rgba(0, 0, 0, 0.2);
                margin-left: 16px;
                vertical-align: middle;
                cursor: pointer;
                transition: color 0.3s;

                &:hover {
                    color: #1890ff;
                }
            }

            .register {
                float: right;
            }
        }
    }
</style>
