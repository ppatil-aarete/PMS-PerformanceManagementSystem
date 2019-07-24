 $(document).ready(function(){
            var options = {
                max_value: 5,
                step_size: 1,
                selected_symbol_type: 'utf8_star',
                initial_value: 0,
                update_input_field_name: $("#appr_dropdown"),
            }
            debugger;
            $(".rateappr").rate(options);

            $(".rateappr").on("change", function(ev, data){
                //console.log(data.from, data.to,"the data start from initial value",data.from," and end ends at ",data.to);
                //console.log("the variable which contains the final updated value is data.to");
               // var score = data.to;
                //console.log(score);
                //$("#score").html(score);
            });

            $(".rateappr").on("updateError", function(ev, jxhr, msg, err){
                console.log("This is a custom error event");
            });

            $(".rateappr").rate("setAdditionalData", {id: 42});
            $(".rateappr").on("updateSuccess", function(ev, data){
                console.log(data);
            });
        });