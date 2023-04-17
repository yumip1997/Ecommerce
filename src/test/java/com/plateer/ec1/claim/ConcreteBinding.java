package com.plateer.ec1.claim;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

//TODO 삭제예정
public class ConcreteBinding {

    // 가시화
    //1. 전략 구현제마다 DI 주입이 끝난 자기자신을 반환하는 매서드를 만들어야 함
        // 1-1. static 챀조변수
        // 1-2. PostConstruct 어노테이션을 이용해 의존성 주입이 끝난 후, 참조변수가 자기자신을 가리키도록
        // 1-3. static 참조변수를 리턴하는 static 메서드를 만들어야함
    //2. 결정자 클래스에 정의
    //3. 업무가 많아질 수록 결정자 클래스는 커지고 -> 오히려 보기 더 불편해질수도 있을 가능성
    //4. 어떤 업무가 어떤 전략들을 가지고 있는지 정적으로 결정됨
    // 만들다 보니, 전략이 증가할 수록 결정자는 더 커지고, 전략 추가 이외 다른 클래스 즉, 결정자도 손봐야함

    // VS

    // 전략 + factory 패턴 사용
    // 단순 Factory 패턴 사용 -> 업무를 key로 전략을 value로 가지는 Factory 생성
    // 결정자 클래스에 하나하나 셋팅할 필요x - 단지 업무를 던져주는 적절히 전략들이 조합돼서 실행됨
    // runtime 시, 업무 별 전략을 동적으로 결정
    // 단점은 전략 생성시, 해당 전략 객체를 가지고 있는 Factory 역시 생성해 줘야함
    @RequiredArgsConstructor
    enum ClaimHolder {
        CANCEL(Arrays.asList(ConcreateValidator1::instance, ConcreateValidator1::instance), ConcreteCreator1::instance, ConcreateCaller::instance, ConcreteVerifier::instance, Arrays.asList(SMSNotifier::instance, EmailNotifier::instance)),
        RA(Arrays.asList(ConcreateValidator1::instance, ConcreateValidator1::instance), ConcreteCreator1::instance, ConcreateCaller::instance, ConcreteVerifier::instance, Arrays.asList(SMSNotifier::instance, EmailNotifier::instance)),
        RW(Arrays.asList(ConcreateValidator1::instance, ConcreateValidator1::instance), ConcreteCreator1::instance, ConcreateCaller::instance, ConcreteVerifier::instance, Arrays.asList(SMSNotifier::instance, EmailNotifier::instance)),
        EA(Arrays.asList(ConcreateValidator1::instance, ConcreateValidator1::instance), ConcreteCreator1::instance, ConcreateCaller::instance, ConcreteVerifier::instance, Arrays.asList(SMSNotifier::instance, EmailNotifier::instance)),
        EW(Arrays.asList(ConcreateValidator1::instance, ConcreateValidator1::instance), ConcreteCreator1::instance, ConcreateCaller::instance, ConcreteVerifier::instance, Arrays.asList(SMSNotifier::instance, EmailNotifier::instance));

        private final List<Supplier<Validator>> validatorSupplier;
        private final Supplier<Creator> creatorSupplier;
        private final Supplier<ExternalCaller> externalCallerSupplier;
        private final Supplier<Verifier> verifierSupplier;
        private final List<Supplier<Notifier>> notifierSuppliers;
    }


    interface Validator { }

    @Component
    static class ConcreateValidator1 implements Validator{
        public static Validator instance = null;
        @PostConstruct
        public void init(){
            //의존성 주입이 끝난 후, validator 참조변수가 자기자신을 가리키도록 변경
            instance = this;
        }

        public static Validator instance(){
            return instance;
        }

    }

    interface Creator { }
    static class ConcreteCreator1 implements Creator {
        public static Creator insatnce = null;
        @PostConstruct
        public void init(){
            insatnce = this;
        }

        public static Creator instance(){
            return insatnce;
        }
    }

    interface ExternalCaller {}

    static class ConcreateCaller implements ExternalCaller {
        public static ExternalCaller insatnce = null;
        @PostConstruct
        public void init(){
            insatnce = this;
        }

        public static ExternalCaller instance(){
            return insatnce;
        }
    }

    interface Verifier {}
    static class ConcreteVerifier implements Verifier {
        public static Verifier insatnce = null;
        @PostConstruct
        public void init(){
            insatnce = this;
        }

        public static Verifier instance(){
            return insatnce;
        }
    }

    interface Notifier {}
    static class SMSNotifier implements Notifier {
        public static Notifier insatnce = null;
        @PostConstruct
        public void init(){
            insatnce = this;
        }

        public static Notifier instance(){
            return insatnce;
        }
    }

    static class EmailNotifier implements Notifier {
        public static Notifier insatnce = null;
        @PostConstruct
        public void init(){
            insatnce = this;
        }

        public static Notifier instance(){
            return insatnce;
        }
    }



}
