const regs = {
    username: /^([0-9]|[a-z]|[A-Z]){5,11}$/ ,
    password: /^([0-9]|[a-z]|[A-Z]|[~!@#$%^&*_.]){6,}$/,
    phone: /^1[3-9]\d{9}$/
}

const verity =(rule,value,reg,callback)=>{
    if(value){
        if(reg.test(value)){
            callback
        }
        else{
            callback(new Error(rule.message))
        }
    }
    else{
        callback
    }
}

export default{
    username:(rule,value,callback)=>{
        return verity(rule,value,regs.username,callback)
    },
    password:(rule,value,callback)=>{
        return verity(rule,value,regs.password,callback)
    },
    phone:(rule,value,callback)=>{
        return verity(rule,value,regs.phone,callback)
    }
}