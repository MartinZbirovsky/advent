package advent.controller;

import advent.dto.requestDto.GlobalInfoMessageDto;
import advent.model.GlobalInfoMessage;
import advent.service.intf.GlobalInfoMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static advent.cons.GeneralCons.PAGE_NUMBER;
import static advent.cons.GeneralCons.PAGE_SIZE;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class GlobalInfoMessageController {
    private final GlobalInfoMessageService messService;

    @GetMapping("")
    public Page<GlobalInfoMessage> getAllInfo(@RequestParam(defaultValue = PAGE_NUMBER) Integer pageNo,
                                                     @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy){
        return messService.findAll(pageNo, pageSize, sortBy);
    }

    @GetMapping("/{id}")
    public GlobalInfoMessage getInfoById(@PathVariable Long id){
        return messService.findById(id);
    }

    @PostMapping("")
    public GlobalInfoMessage addInfo(@Valid @RequestBody GlobalInfoMessageDto globalInfoMessage){
        return messService.addNew(globalInfoMessage);
    }


    @DeleteMapping("/{id}")
    public GlobalInfoMessage deleteInfo(@PathVariable Long id) {
        return messService.deleteById(id);
    }
}
