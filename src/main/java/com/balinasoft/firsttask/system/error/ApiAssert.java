package com.balinasoft.firsttask.system.error;

import com.balinasoft.firsttask.system.error.exception.*;

public class ApiAssert {

    public static void notFound(boolean isThrow) {
        if (isThrow) {
            throw new NotFoundException();
        }
    }

    public static void notFound(boolean isThrow, String error) {
        if (isThrow) {
            throw new NotFoundException(error);
        }
    }

    public static void forbidden(boolean isThrow) {
        if (isThrow) {
            throw new ForbiddenException();
        }
    }

    public static void badRequest(boolean isThrow) {
        if (isThrow) {
            throw new BadRequestException();
        }
    }

    public static void badRequest(boolean isThrow, String error) {
        if (isThrow) {
            throw new BadRequestException(error);
        }
    }

    public static void unprocessable(boolean isThrow) {
        if (isThrow) {
            throw new UnprocessableException();
        }
    }

    public static void unprocessable(boolean isThrow, String error) {
        if (isThrow) {
            throw new UnprocessableException(error);
        }
    }

    public static void internal(boolean isThrow) {
        if (isThrow) {
            throw new InternalServerErrorException();
        }
    }

    public static void internal(boolean isThrow, String error) {
        if (isThrow) {
            throw new InternalServerErrorException(error);
        }
    }
}
