package travelplanner.project.demo.domain.message.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.domain.member.domain.Member;
import travelplanner.project.demo.domain.message.Repository.MessageRepository;
import travelplanner.project.demo.domain.message.domain.Message;
import travelplanner.project.demo.domain.message.dto.request.MessageSendRequest;
import travelplanner.project.demo.global.exception.ApiException;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.util.AuthUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MessageService {
    private final AuthUtil authUtil;
    private final MessageRepository messageRepository;

    // 특정 유저의 메세지 리스트
    public Map<Long, List<Message>> getMessageList (HttpServletRequest request) {
        Member member = authUtil.getCurrentMember(request);
        List<Message> messages = messageRepository.findAllMessagesByUserId(member.getId());

        return messages.stream()
                .collect(Collectors.groupingBy(message ->
                                message.getSenderUserId().equals(member.getId()) ? message.getReceivedUserId() : message.getSenderUserId(),
                        Collectors.collectingAndThen(Collectors.toList(), list -> {
                            list.sort(Comparator.comparing(Message::getCreatedAt));
                            return list;
                        })
                ));
    }


    // 메세지 보내기
    @Transactional
    public void sendMessage (MessageSendRequest request, HttpServletRequest servletRequest) {
        Member sendUser = authUtil.getCurrentMember(servletRequest);
        System.out.println(sendUser.getId() + " " + request.getSendUserId());
        if (!sendUser.getId().equals(request.getSendUserId())) {
            throw new ApiException(ErrorType.LOGIN_USER_AND_SEND_USER_ARE_NOT_SAME);
        }

        Message message = Message.builder()
                .senderUserId(sendUser.getId())
                .receivedUserId(request.getReceivedUserId())
                .messageContent(request.getMessageContent())
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(message);
    }


}