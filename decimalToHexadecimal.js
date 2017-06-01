//jokeuncle 2017-06-01 22:00
function decimalToHexadecimal(n){
	var stack = [];
	while(parseInt(n / 16) != 0){
		stack.push(n % 16);
		n = parseInt(n / 16);
	}
	stack.push(n % 16);
	for(var i=0;i<stack.length;i++){
		var v = stack[i];
		if(v==10){
			stack[i]="A";
		}else if(v==11){
			stack[i]="B";
		}else if(v==12){
			stack[i]="C";
		}else if(v==13){
			stack[i]="D";
		}else if(v==14){
			stack[i]="E";
		}else if(v==15){
			stack[i]="F";
		}else{
			stack[i]=stack[i]
		}
	}
	return "Ox"+stack.reverse().join("");
}