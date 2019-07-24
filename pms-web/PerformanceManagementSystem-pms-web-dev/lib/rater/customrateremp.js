 $(document).ready(function(){
	 debugger;
            var options = {
                max_value: 5,
                step_size: 1,
                selected_symbol_type: 'utf8_star',
                initial_value: 0,
                update_input_field_name: $("#emp_dropdown"),
            }
            
            $(".rateemp").rate(options);

            $(".rateemp").on("change", function(ev, data){
                
            });

            $(".rateemp").on("updateError", function(ev, jxhr, msg, err){
            });

            $(".rateemp").rate("setAdditionalData", {id: 42});
            $(".rateemp").on("updateSuccess", function(ev, data){
            });
        });