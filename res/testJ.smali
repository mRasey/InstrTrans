.class public Lcom/example/test/testJ;
.super Ljava/lang/Object;
.source "testJ.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public test()V
    .registers 2

    .prologue
    .line 5
    const/4 v0, 0x0

    .line 6
    .local v0, "i":I
    return-void
.end method
