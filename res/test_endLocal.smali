.class public Lcom/example/test/test_endLocal;
.super Ljava/lang/Object;
.source "test_endLocal.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 6
    .param p0, "args"    # [Ljava/lang/String;

    .prologue
    const/16 v4, 0xa

    .line 7
    const/4 v0, 0x0

    .line 8
    .local v0, "a":I
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_4
    if-lt v2, v4, :cond_11

    .line 11
    const/16 v1, 0xa

    .line 12
    .local v1, "b":I
    const/4 v2, 0x0

    :goto_9
    if-lt v2, v4, :cond_16

    .line 16
    const-wide v2, 0x4000cccccccccccdL

    .line 17
    .local v2, "i":D
    return-void

    .line 9
    .end local v1    # "b":I
    .local v2, "i":I
    :cond_11
    add-int/lit8 v0, v0, 0x1

    .line 8
    add-int/lit8 v2, v2, 0x1

    goto :goto_4

    .line 13
    .restart local v1    # "b":I
    :cond_16
    add-int/lit8 v0, v0, 0x1

    .line 12
    add-int/lit8 v2, v2, 0x1

    goto :goto_9
.end method
